package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.response.DashboardResponse;
import id.co.mii.clientapp.services.DashboardService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController {
    private DashboardService dashboardService;
    private AuthenticationSessionUtil authenticationSessionUtil;

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
        DashboardResponse response = dashboardService.getAll();
        List<String> authorities = authenticationSessionUtil
                .authentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("authorities", authorities);
        model.addAttribute("training", response.getTraining());
        model.addAttribute("trainee", response.getTrainee());
        model.addAttribute("trainer", response.getTrainee());
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
