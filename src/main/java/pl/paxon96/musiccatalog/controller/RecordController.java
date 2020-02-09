package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.entity.Photo;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.RecordRepository;
import pl.paxon96.musiccatalog.service.CloudinaryService;
import pl.paxon96.musiccatalog.service.ComposerService;
import pl.paxon96.musiccatalog.service.PerformerService;
import pl.paxon96.musiccatalog.service.RecordService;
import pl.paxon96.musiccatalog.util.PhotoValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private ComposerService composerService;
    @Autowired
    private PerformerService performerService;
    @Autowired
    private PhotoValidator photoValidator;

    @GetMapping
    public ModelAndView getRecords(ModelAndView modelAndView){
        modelAndView.getModelMap().clear();
        List<Record> recordList = recordRepository.findAll();
        for (Record record : recordList) {
            if(record.getPhoto() == null){
                record.setPhoto(new Photo());
            }
        }
        modelAndView.addObject("records", recordList);
        modelAndView.setViewName("records");

        return modelAndView;
    }

    @GetMapping(value = "/add")
    public String addRecord(Model model){
        model.asMap().clear();
        model.addAttribute("recordDto", new RecordDto());
        model.addAttribute("composers", composerService.getAllComposers());
        model.addAttribute("performers", performerService.getAllPerformers());
        return "addRecord";
    }

    @PostMapping(value = "/add")
    public ModelAndView addPostRecord(ModelAndView modelAndView, @ModelAttribute("recordDto") @Valid RecordDto recordDto) throws IOException {
        System.out.println(recordDto);
        if(photoValidator.validatePhoto(recordDto.getPhoto())){
           // cloudinaryService.sendImage(recordDto.getPhoto());
        }
        recordService.addRecord(recordDto);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }
}
