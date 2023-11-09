package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // @GetMapping("/dashboard")
    // public String home() {
    // return "coba";
    // }

    @GetMapping("/landing")
    public String landingview(Model model) {
        model.addAttribute("landing", "MCC 81");
        return "auth/landing";
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

    @GetMapping("/dashboard")
    public String dashboardview(Model model) {
        model.addAttribute("title", "MCC 81");
        return "dashboard/index";
    }

    @GetMapping("/404")
    public String error404view(Model model) {
        model.addAttribute("title", "MCC 81");
        return "page-error/404";
    }

    @GetMapping("/500")
    public String error505view(Model model) {
        model.addAttribute("title", "MCC 81");
        return "page-error/500";
    }

    // @GetMapping("/change-password")
    // public String changePasswordview(Model model) {
    //     model.addAttribute("title", "MCC 81");
    //     return "auth/change-password";
    // }
}
