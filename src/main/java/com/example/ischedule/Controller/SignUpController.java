package com.example.ischedule.Controller;

import com.example.ischedule.Model.User;
import com.example.ischedule.Model.UserRole;
import com.example.ischedule.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignUpController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        //TODO: Validate the form data and perform any necessary checks

        String encodedPassword = passwordEncoder.encode(password);
        com.example.ischedule.Model.User newUser = new User(username, email, encodedPassword, UserRole.STUDENT);
        userService.saveUser(newUser);

        //TODO: Authenticate the newly registered user

        // Redirect the user to the home page after successful sign-up
        return "redirect:/home";
    }
}

