package com.example.ischedule.Controller;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    // Add other controller methods for course-related operations

}

