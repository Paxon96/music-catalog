package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.recource.MusicTypeDto;
import pl.paxon96.musiccatalog.service.MusicTypeService;

@Controller
@RequestMapping("/musicTypes")
public class MusicTypeController {

    @Autowired
    private MusicTypeService musicTypeService;

    @GetMapping
    public String getMusicTypes(Model model){
        model.addAttribute("musicTypes", musicTypeService.getAllMusicTypes());
        return "musicTypes";
    }

    @GetMapping(value = "/edit")
    public String editMusicTypes(Model model, @RequestParam("musicTypeId") Long musicTypeId){
        model.asMap().clear();
        model.addAttribute("musicType", musicTypeService.getById(musicTypeId));
        return "editMusicTypes";
    }

    @PostMapping(value = "/edit")
    public ModelAndView editMusicTypesPost(ModelAndView model, @ModelAttribute("musicType") MusicTypeDto musicType){
        musicTypeService.edit(musicType);
        model.getModel().clear();
        model.setViewName("redirect:/musicTypes");
        return model;
    }

    @GetMapping(value = "/add")
    public String addMusicTypes(Model model){
        model.asMap().clear();
        model.addAttribute("musicType", new MusicTypeDto());
        return "addMusicTypes";
    }

    @PostMapping(value = "add")
    public ModelAndView addMusicTypesPost(ModelAndView modelAndView, @ModelAttribute("musicType") MusicTypeDto musicType){
        if(!musicType.getName().trim().equalsIgnoreCase(""))
            musicTypeService.add(musicType);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }
}
