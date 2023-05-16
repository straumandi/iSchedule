package com.example.ischedule.Service;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();
    /*
     * Find all courses that belong to a user.
     */
    List<Course>getEnrolledCourses(User user);
    Optional<Course> getCourseById(int id);
    void saveCourse(Course course);
    void deleteCourse(int id);
}


