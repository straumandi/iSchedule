package com.example.ischedule.Repository;

import com.example.ischedule.Model.Course;
import com.example.ischedule.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByUser(User user);
}
