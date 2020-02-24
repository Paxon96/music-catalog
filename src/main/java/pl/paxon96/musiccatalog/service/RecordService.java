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
        List<Performer> performers = getPerformers(recordDto);
        List<Composer> composers = getComposers(recordDto);

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

        setYear(recordDto, record);
        setDescription(recordDto, record);
        setPerformers(record, performers);
        setComposers(record, composers);
        recordRepository.save(record);
        for (Performer performer : performers) {
            performerRepository.save(performer);
        }

        for (Composer composer : composers) {
            composerRepository.save(composer);
        }
        return record;
    }

    private void setDescription(RecordDto recordDto, Record record) {
        if (!recordDto.getDescription().trim().equalsIgnoreCase("")) {
            record.setDescription(recordDto.getDescription());
        }
    }

    @Transactional
    public Record editRecord(RecordDto recordDto, Long recordId){
        Optional<Record> optionalRecord = recordRepository.findById(recordId);
        if(optionalRecord.isPresent()){
            Record record = optionalRecord.get();
//            List<Performer> recordPerformers = record.getPerformers();
//            for (Performer performer : recordPerformers) {
//                performer.removeRecord(record);
//                //performerRepository.save(performer);
//            }
//
//            List<Composer> recordComposers = record.getComposers();
//            for (Composer composer : recordComposers) {
//                composer.removeRecord(record);
//                //composerRepository.save(composer);
//            }



            List<Performer> performers = getPerformers(recordDto);

            List<Composer> composers = getComposers(recordDto);

            record.setTitle(recordDto.getTitle());
            record.setFormat(formatRepository.findById(recordDto.getFormatId()).get());
            record.setMusicType(musicTypeRepository.findById(recordDto.getMusicTypeId()).get());
            record.setComposers(new ArrayList<>());
            record.setRecordType(recordDto.getRecordType());
            record.setPerformers(new ArrayList<>());
            record.setRecordAmount(Integer.valueOf(recordDto.getRecordAmount()));
            record.setIsReproduction(recordDto.getIsReproduction());

            setYear(recordDto, record);
            setDescription(recordDto, record);
            setPerformers(record, performers);
            setComposers(record, composers);

            recordRepository.save(record);

            for (Performer performer : performers) {
                performerRepository.save(performer);
            }

            for (Composer composer : composers) {
                composerRepository.save(composer);
            }
            return record;
        }

        return null;
    }

    private void setComposers(Record record, List<Composer> composers) {
        if (!composers.isEmpty()) {
            for (Composer composer : composers) {
                composer.addRecord(record);
            }
        }
    }

    private void setPerformers(Record record, List<Performer> performers) {
        if (!performers.isEmpty()) {
            for (Performer performer : performers) {
                performer.addRecord(record);
            }
        }
    }

    private void setYear(RecordDto recordDto, Record record) {
        if (!recordDto.getYear().trim().equalsIgnoreCase("")) {
            record.setYear(Integer.valueOf(recordDto.getYear()));
        }
    }

    private List<Composer> getComposers(RecordDto recordDto) {
        List<Composer> composers = new ArrayList<>();
        if(!arrayValidator.isArrayEmpty(recordDto.getComposerIdArray())){
            for (Long composerId : arrayValidator.getNonNullIndexes(recordDto.getComposerIdArray())) {
                Optional<Composer> optionalComposer = composerRepository.findById(composerId);
                optionalComposer.ifPresent(composers::add);
            }
        }
        return composers;
    }

    private List<Performer> getPerformers(RecordDto recordDto) {
        List<Performer> performers = new ArrayList<>();

        if(!arrayValidator.isArrayEmpty(recordDto.getPerformerIdArray())) {
            for (Long performerId : arrayValidator.getNonNullIndexes(recordDto.getPerformerIdArray())) {
                Optional<Performer> optionalPerformer = performerRepository.findById(performerId);
                optionalPerformer.ifPresent(performers::add);
            }
        }
        return performers;
    }

    public Record getById(Long id){
        Optional<Record> recordOptional = recordRepository.findById(id);
        return recordOptional.orElse(null);
    }


}
