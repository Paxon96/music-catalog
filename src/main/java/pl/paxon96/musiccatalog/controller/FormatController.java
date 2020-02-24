package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paxon96.musiccatalog.recource.FormatDto;
import pl.paxon96.musiccatalog.service.FormatService;

@Controller
@RequestMapping("/formats")
public class FormatController {

    @Autowired
    private FormatService formatService;

    @GetMapping
    public String getFormats(Model model){
        model.addAttribute("formats", formatService.getAllFormats());
        return "formats";
    }

    @GetMapping(value = "/edit")
    public String editFormat(Model model, @RequestParam("formatId") Long formatId){
        model.asMap().clear();
        model.addAttribute("format", formatService.getById(formatId));
        return "editFormat";
    }

    @PostMapping(value = "/edit")
    public ModelAndView editFormatPost(ModelAndView model, @ModelAttribute("format") FormatDto format){
        formatService.edit(format);
        model.getModel().clear();
        model.setViewName("redirect:/formats");
        return model;
    }

    @GetMapping(value = "/add")
    public String addFormat(Model model){
        model.asMap().clear();
        model.addAttribute("format", new FormatDto());
        return "addFormat";
    }

    @PostMapping(value = "add")
    public ModelAndView addFormatPost(ModelAndView modelAndView,  @ModelAttribute("format") FormatDto format){
        if(!format.getName().trim().equalsIgnoreCase(""))
            formatService.add(format);
        modelAndView.setViewName("redirect:add");
        return modelAndView;
    }

}
