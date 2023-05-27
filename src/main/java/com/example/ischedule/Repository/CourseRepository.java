package com.example.ischedule.Repository;

import com.example.ischedule.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    /*
     * Find all courses that belong to a user and eagerly fetch the enrolledUsers collection.
     */
    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.enrolledUsers u WHERE u.id = :userId")
    List<Course> getEnrolledCourses(@Param("userId") int userId);

}


