package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/LandingPage")

public class LandingController {

    @GetMapping
    public String landingPage(Model model){
        model.addAttribute("pageTitle","Welcome to Our Landing Page");
        return "landing-page";
        
    }
    
}
