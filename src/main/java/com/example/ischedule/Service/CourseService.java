package com.example.ischedule.Service;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();

    List<Course> getEnrolledCourses(int userId);

    Optional<Course> getCourseById(int id);

    void saveCourse(Course course);

    void deleteCourse(int id);

    void enrollUserInCourse(User user, Optional<Course> course);
}


