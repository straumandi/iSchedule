package com.example.ischedule.Controller;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public String addCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/home";
    }

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("allCourses", allCourses);
        return "redirect:/home";
    }

}

