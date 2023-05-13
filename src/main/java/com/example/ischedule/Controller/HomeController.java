package com.example.ischedule.Controller;

import com.example.ischedule.Service.CustomUserDetails;
import com.example.ischedule.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService; // UserService to retrieve the authenticated user

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);

        CustomUserDetails currentUser = userService.getCurrentUser();

        // Determine the user's role and set corresponding attributes in the model
        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASSISTANT"))) {
            model.addAttribute("isAssistant", true);
        } else {
            model.addAttribute("isAssistant", false);
        }

        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            model.addAttribute("isStudent", true);
        } else {
            model.addAttribute("isStudent", false);
        }

        //TODO: Add other necessary data to the model

        return "home";
    }

}

