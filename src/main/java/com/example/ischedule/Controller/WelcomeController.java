package com.example.ischedule.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    // Mapping for the root URL ("/") to redirect to the welcome page
    @GetMapping("/")
    public String redirectToWelcome() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }
}

