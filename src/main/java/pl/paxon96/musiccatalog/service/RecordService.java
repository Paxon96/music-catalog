package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Performer;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.ComposerRepository;
import pl.paxon96.musiccatalog.repository.PerformerRepository;
import pl.paxon96.musiccatalog.repository.RecordRepository;

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

    public void addRecord(RecordDto recordDto){
        Performer performer = null;
        if(recordDto.getPerformerId() != null) {
            Optional<Performer> optionalPerformer = performerRepository.findById(recordDto.getPerformerId());
            if(optionalPerformer.isPresent()){
                performer = optionalPerformer.get();
            }
        }

        Record record = Record.builder()
                .title(recordDto.getTitle())
                .type("test")
                .performers(new ArrayList<>())
                .build();

        performer.addRecord(record);

        recordRepository.save(record);
        performerRepository.save(performer);

    }

}
