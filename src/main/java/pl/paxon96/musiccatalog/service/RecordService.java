package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Composer;
import pl.paxon96.musiccatalog.entity.Performer;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.*;

import java.util.ArrayList;
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

//    public Record addRecord(RecordDto recordDto){
//        Performer performer = null;
//        Composer composer = null;
////        if(recordDto.getPerformerIdArray() != null) {
////            Optional<Performer> optionalPerformer = performerRepository.findById(recordDto.getPerformerIdArray());
////            if(optionalPerformer.isPresent()){
////                performer = optionalPerformer.get();
////            }
////        }
////        if(recordDto.getComposerIdArray() != null) {
////            Optional<Composer> optionalComposer = composerRepository.findById(recordDto.getComposerIdArray());
////            if(optionalComposer.isPresent()){
////                composer = optionalComposer.get();
////            }
////        }
////
////        Record record = Record.builder()
////                .title(recordDto.getTitle())
////                .format(formatRepository.findById(recordDto.getFormatId()).get())
////                .musicType(musicTypeRepository.findById(recordDto.getMusicTypeIdArray()).get())
////                .composers(new ArrayList<>())
////                .performers(new ArrayList<>())
////                .isReproduction(recordDto.getIsReproduction())
////                .year(recordDto.getYear())
////                .description(recordDto.getDescription())
////                .build();
//
//        if (performer != null) {
//            performer.addRecord(record);
//        }
//        if (composer != null) {
//            composer.addRecord(record);
//        }
//        recordRepository.save(record);
//        performerRepository.save(performer);
//        composerRepository.save(composer);
//
//        return record;
//    }



}
