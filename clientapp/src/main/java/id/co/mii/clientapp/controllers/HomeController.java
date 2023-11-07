package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "coba";
    }

    @GetMapping("/login")
    public String loginview(Model model) {
        model.addAttribute("title", "MCC 81");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerview(Model model) {
        model.addAttribute("title", "MCC 81");
        return "auth/register";
    }
}
