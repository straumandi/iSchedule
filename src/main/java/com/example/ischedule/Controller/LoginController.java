package com.example.ischedule.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    //TODO: if user is logged in --> redirect:/home

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/home")
    public String handleSuccessfulLogin() {
        return "redirect:/home";
    }
}

