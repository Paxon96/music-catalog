package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Composer;
import pl.paxon96.musiccatalog.recource.ComposerDto;
import pl.paxon96.musiccatalog.repository.ComposerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComposerService {

    @Autowired
    private ComposerRepository composerRepository;

    public List<ComposerDto> getAllComposers() {
        return composerRepository.findAll()
                .stream()
                .map(com -> ComposerDto.builder().name(com.getName()).id(com.getId()).build())
                .collect(Collectors.toList());
    }

    public ComposerDto getById(Long id){
        Optional<Composer> performerOptional = composerRepository.findById(id);
        return performerOptional.map(com -> ComposerDto.builder().id(com.getId()).name(com.getName()).build()).orElse(null);
    }

    public void edit(ComposerDto composerDto){
        Optional<Composer> composerOptional = composerRepository.findById(composerDto.getId());
        if(composerOptional.isPresent()){
            Composer composer = composerOptional.get();
            composer.setName(composerDto.getName());
            composerRepository.save(composer);
        }
    }

    public void add(ComposerDto composerDto) {
        Composer composer = Composer.builder().name(composerDto.getName()).build();
        composerRepository.save(composer);
    }
}
