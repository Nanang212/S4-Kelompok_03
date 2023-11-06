package id.co.mii.clientapp.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String errorhandler(HttpServletRequest httprequest, Model model){
        String errorMessage = "";
        String errorTitle = "";
        Integer httpErrorCode = getErrorCode(httprequest);
        switch (httpErrorCode) {
            case 404: {
                errorTitle = "Http Error Code: 404";
                errorMessage = "Bad Request!!!";
                break;
            }
            case 500: {
                errorTitle = "Http Error Code: 500.";
                errorMessage = "Unauthorized";
                break;
            }
          
        }
        model.addAttribute("statusCode",httpErrorCode);
        model.addAttribute("titleError",errorTitle);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }
}

