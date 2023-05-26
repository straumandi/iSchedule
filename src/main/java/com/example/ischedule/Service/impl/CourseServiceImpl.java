package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;
import com.example.ischedule.Repository.CourseRepository;
import com.example.ischedule.Repository.CourseScheduleRepository;
import com.example.ischedule.Repository.UserRepository;
import com.example.ischedule.Service.CourseService;
import com.example.ischedule.Service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CourseScheduleRepository courseScheduleRepository, UserService userService, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Course> getAllCourses() {
       return courseRepository.findAll();
    }

    @Override
    public List<Course> getEnrolledCourses(int userId) {
        System.out.println("Enrolled Courses: " + courseRepository.getEnrolledCourses(userId));
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
        courseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void enrollUserInCourse(User user, Optional<Course> course) {
        if (course.isPresent()) {
            Course enrolledCourse = course.get();
            Set<User> enrolledUsers = enrolledCourse.getEnrolledUsers();

            // Make sure the user is persisted in the database
            User persistedUser = userRepository.save(user);

            // Associate the persisted user with the course
            enrolledUsers.add(persistedUser);

            courseRepository.save(enrolledCourse);
        } else {
            // TODO: Handle the case when the course is not found
            // TODO: Create global error Interceptor!
        }
    }





}

