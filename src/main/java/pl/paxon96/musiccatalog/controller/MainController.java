package pl.paxon96.musiccatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.paxon96.musiccatalog.entity.User;
import pl.paxon96.musiccatalog.repository.RecordRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainPage(Model model){
        return "index";
    }

    @GetMapping(value = "login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

//    @PostMapping(value = "login")
//    public ModelAndView postMainPage(@ModelAttribute("user") @Valid User user, RedirectAttributes redirect, ModelAndView model) {
//
//        if(user.getLogin() == null){
//            model.setViewName("redirect:/login");
//            redirect.addFlashAttribute("user", user);
//            redirect.addFlashAttribute("invalidPassword", "Niepoprawne hasło");
//            return model;
//        }
//        return model;
//    }

    @PostMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

}
