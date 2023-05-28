package com.example.ischedule.Repository;

import com.example.ischedule.Model.User;
import com.example.ischedule.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /*
     * Find a user by their email address.
     */
    User findByEmail(String email);

    /*
     * Find a user by their username.
     */
    User findByUsername(String username);

    /*
     * Find all users with a given role.
     */
    List<User> findByRole(UserRole role);

    /*
     * Find all users for multiple roles.
     */
    List<User> getUsersByRoleIn(List<UserRole> roles);


    /*
     * Find all users by course id.
     */
    @Query("SELECT u FROM User u JOIN CourseSchedule cs JOIN cs.course c WHERE c.id = :courseId")
    List<User> findByCourseId(@Param("courseId") int courseId);

    /*
     * Find all users.
     */
    List<User> findAll();

    //Set<Course> getEnrolledCoursesByUser(User user);
}
