package com.example.ischedule.Service;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getCoursesByUser(User user);

    List<Course> getAllCourses();
    Optional<Course> getCourseById(int id);
    void saveCourse(Course course);
    void deleteCourse(int id);
}


