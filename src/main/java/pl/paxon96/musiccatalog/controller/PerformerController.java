package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.recource.PerformerDto;
import pl.paxon96.musiccatalog.service.PerformerService;

@Controller
@RequestMapping("/performers")
public class PerformerController {

    @Autowired
    private PerformerService performerService;

    @GetMapping
    public String getPerformers(Model model){
        model.addAttribute("performers", performerService.getAllPerformers());
        return "performers";
    }

    @GetMapping(value = "/edit")
    public String editPerformer(Model model, @RequestParam("performerId") Long performerId){
        model.asMap().clear();
        model.addAttribute("performer", performerService.getById(performerId));
        return "editPerformer";
    }

    @PostMapping(value = "/edit")
    public ModelAndView editPerformerPost(ModelAndView model, @ModelAttribute("performer") PerformerDto performer){
        performerService.edit(performer);
        model.getModel().clear();
        model.setViewName("redirect:edit?performerId={id}".replace("{id}", performer.getId().toString()));
        return model;
    }

    @GetMapping(value = "/add")
    public String addPerformer(Model model){
        model.asMap().clear();
        model.addAttribute("performer", new PerformerDto());
        return "addPerformer";
    }

    @PostMapping(value = "add")
    public ModelAndView addPerformerPost(ModelAndView modelAndView, @ModelAttribute("performer") PerformerDto performer){
        if(!performer.getName().trim().equalsIgnoreCase(""))
            performerService.add(performer);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }
}
