package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Composer;
import pl.paxon96.musiccatalog.entity.Performer;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.*;
import pl.paxon96.musiccatalog.util.ArrayValidator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    private PerformerRepository performerRepository;
    @Autowired
    private ComposerRepository composerRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private MusicTypeRepository musicTypeRepository;
    @Autowired
    private FormatRepository formatRepository;
    @Autowired
    private ArrayValidator arrayValidator;

    @Transactional
    public Record addRecord(RecordDto recordDto){
        List<Performer> performers = new ArrayList<>();
        List<Composer> composers = new ArrayList<>();

        if(!arrayValidator.isArrayEmpty(recordDto.getPerformerIdArray())) {
            for (Long performerId : arrayValidator.getNonNullIndexes(recordDto.getPerformerIdArray())) {
                Optional<Performer> optionalPerformer = performerRepository.findById(performerId);
                optionalPerformer.ifPresent(performers::add);
            }
        }

        if(!arrayValidator.isArrayEmpty(recordDto.getComposerIdArray())){
            for (Long composerId : arrayValidator.getNonNullIndexes(recordDto.getComposerIdArray())) {
                Optional<Composer> optionalComposer = composerRepository.findById(composerId);
                optionalComposer.ifPresent(composers::add);
            }
        }

        Record record = Record.builder()
                .title(recordDto.getTitle())
                .format(formatRepository.findById(recordDto.getFormatId()).get())
                .musicType(musicTypeRepository.findById(recordDto.getMusicTypeId()).get())
                .composers(new ArrayList<>())
                .recordType(recordDto.getRecordType())
                .performers(new ArrayList<>())
                .recordAmount(Integer.valueOf(recordDto.getRecordAmount()))
                .isReproduction(recordDto.getIsReproduction())
                .build();

        if(!recordDto.getYear().trim().equalsIgnoreCase("")){
            record.setYear(Integer.valueOf(recordDto.getYear()));
        }
        if(!recordDto.getDescription().trim().equalsIgnoreCase("")){
            record.setDescription(recordDto.getDescription());
        }
        if (!performers.isEmpty()) {
            for (Performer performer : performers) {
                performer.addRecord(record);
            }
        }
        if (!composers.isEmpty()) {
            for (Composer composer : composers) {
                composer.addRecord(record);
            }
        }
        recordRepository.save(record);
        for (Performer performer : performers) {
            performerRepository.save(performer);
        }

        for (Composer composer : composers) {
            composerRepository.save(composer);
        }
        return record;
    }

    public Record getById(Long id){
        Optional<Record> recordOptional = recordRepository.findById(id);
        return recordOptional.orElse(null);
    }


}
