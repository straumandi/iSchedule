package com.example.ischedule.Controller;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;
import com.example.ischedule.Repository.CourseRepository;
import com.example.ischedule.Service.CourseService;
import com.example.ischedule.Service.UserService;
import com.example.ischedule.security.CustomUserDetails;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {

    private final UserService userService; // UserService to retrieve the authenticated user
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final EntityManager entityManager;

    public HomeController(UserService userService, CourseService courseService, CourseRepository courseRepository, EntityManager entityManager) {
        this.userService = userService;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/home")
    @Transactional
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
        User currentUserObject = new User(currentUser.getUsername(), currentUser.getUser().getEmail(), currentUser.getPassword(), currentUser.getUser().getRole());
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
            // Get the enrolled courses for the student and add them to the model
            List<Course> enrolledCourses = courseService.getEnrolledCourses(currentUserObject.getId());
            System.out.println("courses user enrolled in: " + enrolledCourses.toString());
            model.addAttribute("enrolledCourses", enrolledCourses);
        } else {
            model.addAttribute("isStudent", false);
        }

        //TODO: Add other necessary data to the model
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("allCourses", allCourses);

        return "home";
    }



    @Transactional
    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam("courseId") int courseId, Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        Optional<Course> course = courseService.getCourseById(courseId);
        course.ifPresent(c -> {
            Set<User> enrolledUsers = c.getEnrolledUsers();
            enrolledUsers.size(); // Access the collection to trigger loading
            System.out.println("course is present");
        });

        // Get the user by username
        User user = userService.getUserByUsername(username);
        // Enroll the user in the course
        courseService.enrollUserInCourse(user, course);
        System.out.println("after enrollUserInCourse");
        return "redirect:/home";
    }
}

