package com.example.ischedule.Controller;
/*
 * iSchedule
 * <Description>
 * Author: andi_
 * Last Change: 24.05.2023
 */

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;
import com.example.ischedule.Service.CourseService;
import com.example.ischedule.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EnrollmentController {

    private final CourseService courseService;
    private final UserService userService;

    public EnrollmentController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam("courseId") int courseId, Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        // Get the course by its ID
        Optional<Course> course = courseService.getCourseById(courseId);

        // Get the user by username
        User user = userService.getUserByUsername(username);

        // Enroll the user in the course
        courseService.enrollUserInCourse(user, course);

        // Redirect user to root, in this case /home because automatic redirect when authenticated.
        return "redirect:/";
    }
}

