package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;
import com.example.ischedule.Repository.CourseRepository;
import com.example.ischedule.Repository.UserRepository;
import com.example.ischedule.Service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getEnrolledCourses(int userId) {
        return courseRepository.getEnrolledCourses(userId);
    }

    @Override
    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(int id) {
        try {
            courseRepository.deleteById(id);
            System.out.println("Course deleted successfully. ID: " + id);
        } catch (Exception e) {
            System.out.println("Error deleting course with ID: " + id);
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void enrollUserInCourse(User user, Optional<Course> course) {
        if (course.isPresent()) {
            Course enrolledCourse = course.get();
            Set<User> enrolledUsers = enrolledCourse.getEnrolledUsers();

            // Make sure the user is persisted in the database
            User persistedUser = userRepository.save(user);

            // Associate the user with the course
            enrolledUsers.add(persistedUser);
            enrolledCourse.setEnrolledUsers(enrolledUsers);

            // Associate the course with the user
            Set<Course> enrolledCourses = persistedUser.getEnrolledCourses();
            enrolledCourses.add(enrolledCourse);
            persistedUser.setEnrolledCourses(enrolledCourses);

            // Save the updated entities
            courseRepository.save(enrolledCourse);
            userRepository.save(persistedUser);
        }
    }
}

