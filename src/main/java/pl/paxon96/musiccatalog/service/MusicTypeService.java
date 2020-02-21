package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.MusicType;
import pl.paxon96.musiccatalog.recource.MusicTypeDto;
import pl.paxon96.musiccatalog.repository.MusicTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicTypeService {

    @Autowired
    private MusicTypeRepository musicTypeRepository;

    public List<MusicTypeDto> getAllMusicTypes() {
        return musicTypeRepository.findAll()
                .stream()
                .map(mt -> MusicTypeDto.builder().name(mt.getName()).id(mt.getId()).build())
                .collect(Collectors.toList());
    }

    public MusicTypeDto getById(Long id){
        Optional<MusicType> optionalMusicType = musicTypeRepository.findById(id);
        return optionalMusicType.map(mt -> MusicTypeDto.builder().id(mt.getId()).name(mt.getName()).build()).orElse(null);
    }

    public void edit(MusicTypeDto musicTypeDto){
        Optional<MusicType> optionalMusicType = musicTypeRepository.findById(musicTypeDto.getId());
        if(optionalMusicType.isPresent()){
            MusicType musicType = optionalMusicType.get();
            musicType.setName(musicTypeDto.getName());
            musicTypeRepository.save(musicType);
        }
    }

    public void add(MusicTypeDto musicTypeDto) {
        MusicType musicType = MusicType.builder().name(musicTypeDto.getName()).build();
        musicTypeRepository.save(musicType);
    }

}
