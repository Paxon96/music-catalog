package pl.paxon96.musiccatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.musiccatalog.entity.Format;
import pl.paxon96.musiccatalog.recource.FormatDto;
import pl.paxon96.musiccatalog.repository.FormatRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormatService {

    @Autowired
    private FormatRepository formatRepository;

    public List<FormatDto> getAllFormats() {
        return formatRepository.findAll()
                .stream()
                .map(format -> FormatDto.builder().name(format.getName()).id(format.getId()).build())
                .collect(Collectors.toList());
    }

    public FormatDto getById(Long id){
        Optional<Format> optionalFormat = formatRepository.findById(id);
        return optionalFormat.map(format -> FormatDto.builder().id(format.getId()).name(format.getName()).build()).orElse(null);
    }

    public void edit(FormatDto formatDto){
        Optional<Format> optionalFormat = formatRepository.findById(formatDto.getId());
        if(optionalFormat.isPresent()){
            Format format = optionalFormat.get();
            format.setName(formatDto.getName());
            formatRepository.save(format);
        }
    }

    public void add(FormatDto formatDto) {
        Format format = Format.builder().name(formatDto.getName()).build();
        formatRepository.save(format);
    }
}
