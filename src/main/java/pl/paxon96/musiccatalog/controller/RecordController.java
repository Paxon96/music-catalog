package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.entity.Photo;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.RecordRepository;
import pl.paxon96.musiccatalog.service.*;
import pl.paxon96.musiccatalog.util.PhotoValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
    @Autowired
    private MusicTypeService musicTypeService;
    @Autowired
    private FormatService formatService;

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

//    @GetMapping
//    public String getRecord(Model model, @RequestParam("recordId") int recordId){
//
//    }

    @GetMapping(value = "/add")
    public String addRecord(Model model){
        model.asMap().clear();

        model.addAttribute("composers", composerService.getAllComposers());
        model.addAttribute("performers", performerService.getAllPerformers());
        model.addAttribute("musicTypes", musicTypeService.getAllMusicTypes());
        model.addAttribute("formats", formatService.getAllFormats());
        model.addAttribute("recordDto", new RecordDto());
        return "addRecord";
    }

    @PostMapping(value = "/add")
    public ModelAndView addPostRecord(ModelAndView modelAndView, @ModelAttribute("recordDto") @Valid RecordDto recordDto) throws IOException {
        System.out.println(recordDto);
        if(photoValidator.validatePhoto(recordDto.getPhoto())){
           // cloudinaryService.sendImage(recordDto.getPhoto());
        }
        //recordService.addRecord(recordDto);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }
}
