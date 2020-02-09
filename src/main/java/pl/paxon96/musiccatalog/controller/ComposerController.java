package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.recource.ComposerDto;
import pl.paxon96.musiccatalog.service.ComposerService;

@Controller
@RequestMapping("/composers")
public class ComposerController {

    @Autowired
    private ComposerService composerService;

    @GetMapping
    public String getComposers(Model model){
        model.addAttribute("composers", composerService.getAllComposers());
        return "composers";
    }

    @GetMapping(value = "/edit")
    public String editComposers(Model model, @RequestParam("composerId") Long composerId){
        model.asMap().clear();
        model.addAttribute("composer", composerService.getById(composerId));
        return "editComposer";
    }

    @PostMapping(value = "/edit")
    public ModelAndView editComposersPost(ModelAndView model, @ModelAttribute("composer") ComposerDto composer){
        composerService.edit(composer);
        model.getModel().clear();
        model.setViewName("redirect:edit?composerId={id}".replace("{id}", composer.getId().toString()));
        return model;
    }

    @GetMapping(value = "/add")
    public String addComposer(Model model){
        model.asMap().clear();
        model.addAttribute("composer", new ComposerDto());
        return "addComposer";
    }

    @PostMapping(value = "add")
    public ModelAndView addComposerPost(ModelAndView modelAndView, @ModelAttribute("composer") ComposerDto composer){
        if(!composer.getName().trim().equalsIgnoreCase(""))
            composerService.add(composer);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }
}
