package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Performer;
import pl.paxon96.musiccatalog.recource.PerformerDto;
import pl.paxon96.musiccatalog.repository.PerformerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerformerService {

    @Autowired
    private PerformerRepository performerRepository;

    public List<PerformerDto> getAllPerformers() {
        return performerRepository.findAll()
                .stream()
                .map(per -> PerformerDto.builder().name(per.getName()).id(per.getId()).build())
                .collect(Collectors.toList());
    }

    public PerformerDto getById(Long id){
        Optional<Performer> performerOptional = performerRepository.findById(id);
        return performerOptional.map(performer -> PerformerDto.builder().id(performer.getId()).name(performer.getName()).build()).orElse(null);
    }

    public void edit(PerformerDto performerDto){
        Optional<Performer> performerOptional = performerRepository.findById(performerDto.getId());
        if(performerOptional.isPresent()){
            Performer performer = performerOptional.get();
            performer.setName(performerDto.getName());
            performerRepository.save(performer);
        }
    }

    public void add(PerformerDto performerDto){
        Performer performer = Performer.builder().name(performerDto.getName()).build();
        performerRepository.save(performer);
    }
}
