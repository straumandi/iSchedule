package com.example.ischedule.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    //TODO: if user logged in link to home when /login

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/home")
    public String handleSuccessfulLogin() {
        return "redirect:/home";
    }
    
    /* @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        // You can retrieve additional user information from the authentication object
        // and pass it to the model to display on the home page
        model.addAttribute("username", username);
        return "home";
    } */

}

