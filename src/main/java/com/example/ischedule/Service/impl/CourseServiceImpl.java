package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.CourseSchedule;
import com.example.ischedule.Model.User;
import com.example.ischedule.Repository.CourseRepository;
import com.example.ischedule.Repository.CourseScheduleRepository;
import com.example.ischedule.Service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CourseScheduleRepository courseScheduleRepository) {
        this.courseRepository = courseRepository;
        this.courseScheduleRepository = courseScheduleRepository;
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

    @Override
    public void enrollUserInCourse(User user, Optional<Course> course) {
        if (course.isPresent()) {
            Course enrolledCourse = course.get();
            Set<User> enrolledUsers = enrolledCourse.getEnrolledUsers();
            enrolledUsers.add(user);
            enrolledCourse.setEnrolledUsers(enrolledUsers);
            courseRepository.save(enrolledCourse);
        } else {
            //TODO: Handle the case when the course is not found
            //TODO: Create global error Interceptor!
        }
    }


}

