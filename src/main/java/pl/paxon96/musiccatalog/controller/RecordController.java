package pl.paxon96.musiccatalog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.paxon96.musiccatalog.entity.Photo;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.filter.RecordFilterParameters;
import pl.paxon96.musiccatalog.filter.RecordSpecification;
import pl.paxon96.musiccatalog.recource.RecordDto;
import pl.paxon96.musiccatalog.repository.RecordRepository;
import pl.paxon96.musiccatalog.service.*;
import pl.paxon96.musiccatalog.util.ArrayValidator;
import pl.paxon96.musiccatalog.util.PhotoValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/records")
@Log4j2
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
    @Autowired
    private ArrayValidator arrayValidator;


    @GetMapping
    public ModelAndView getRecords(
            ModelAndView modelAndView,
            @RequestParam(required = false)@Valid String title,
            @RequestParam(required = false)@Valid String composer,
            @RequestParam(required = false)@Valid String performer,
            @RequestParam(required = false)@Valid String isReproduction,
            @RequestParam(required = false)@Valid String musicType,
            @RequestParam(required = false)@Valid String format,
            @RequestParam(required = false)@Valid String recordType,
            @RequestParam(required = false)@Valid String year,
            @RequestParam(required = false)@Valid String recordsAmount
    ) {
        Boolean reproduction = null;
        if(isReproduction != null){
            if(isReproduction.equalsIgnoreCase("on")){
                reproduction = true;
            }else{
                reproduction = false;
            }
        }
        RecordFilterParameters filterParameters = RecordFilterParameters.builder()
                .composer(composer)
                .format(format)
                .isReproduction(reproduction)
                .musicType(musicType)
                .title(title)
                .performer(performer)
                .recordType(recordType)
                .year(year)
                .recordsAmount(recordsAmount)
                .build();
        log.info("Filter parameters: " + filterParameters);

        modelAndView.getModelMap().clear();
        Page<Record> recordList = recordRepository.findAll(new RecordSpecification(filterParameters), PageRequest.of(0, 100));
        for (Record record : recordList) {
            if (record.getPhoto() == null) {
                record.setPhoto(new Photo());
            }
        }
        modelAndView.addObject("records", recordList);
        modelAndView.addObject("filterParams", filterParameters);
        modelAndView.setViewName("records");

        return modelAndView;
    }

    @GetMapping(value = "/{recordId}")
    public ModelAndView getRecord(ModelAndView modelAndView, @PathVariable("recordId") Long recordId) {
        modelAndView.getModelMap().clear();
        Record record = recordService.getById(recordId);
        if (record.getPhoto() == null) {
            record.setPhoto(new Photo());
        }
        modelAndView.addObject("record", record);
        modelAndView.setViewName("recordById");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public String addRecord(Model model) {
        model.addAttribute("composers", composerService.getAllComposers());
        model.addAttribute("performers", performerService.getAllPerformers());
        model.addAttribute("musicTypes", musicTypeService.getAllMusicTypes());
        model.addAttribute("formats", formatService.getAllFormats());
        model.addAttribute("recordDto", new RecordDto());
        return "addRecord";
    }

    @PostMapping(value = "/add")
    public ModelAndView addPostRecord(ModelAndView modelAndView, @ModelAttribute("recordDto") @Valid RecordDto recordDto, RedirectAttributes redirect) throws IOException {
        modelAndView.setViewName("redirect:add");
        boolean somethingToAlert = false;
        somethingToAlert = isSomethingToAlert(recordDto, redirect, somethingToAlert);
        if (somethingToAlert) {
            return modelAndView;
        }
        log.info(recordDto);
        Record record = recordService.addRecord(recordDto);
        if (photoValidator.validatePhoto(recordDto.getPhoto())) {
            cloudinaryService.sendImage(recordDto.getPhoto(), record.getId());
        }

        return modelAndView;
    }

    private boolean isSomethingToAlert(@ModelAttribute("recordDto") @Valid RecordDto recordDto, RedirectAttributes redirect, boolean somethingToAlert) {
        if (arrayValidator.isArrayEmpty(recordDto.getPerformerIdArray())) {
            redirect.addFlashAttribute("noPerformers", "Musisz wskazać chociaż jednego wykonawcę");
            somethingToAlert = true;
        }
        if (arrayValidator.isArrayEmpty(recordDto.getComposerIdArray())) {
            redirect.addFlashAttribute("noComposers", "Musisz wskazać chociaż jednego kompozytora");
            somethingToAlert = true;
        }
        if (!recordDto.getYear().trim().equalsIgnoreCase("") && recordDto.getYear().length() < 4 || recordDto.getYear().length() > 4) {
            redirect.addFlashAttribute("invalidYear", "Zły format");
            somethingToAlert = true;
        }
        if (recordDto.getTitle().trim().equalsIgnoreCase("")) {
            redirect.addFlashAttribute("noTitle", "Brak tytułu");
            somethingToAlert = true;
        }
        if (!recordDto.getRecordAmount().trim().equalsIgnoreCase("")) {
            try {
                Integer.parseInt(recordDto.getRecordAmount());
            } catch (NumberFormatException ex) {
                redirect.addFlashAttribute("invalidRecordAmount", "Podana wartość nie jest liczbą");
                somethingToAlert = true;
            }
        } else {
            recordDto.setRecordAmount("1");
        }
        return somethingToAlert;
    }
}
