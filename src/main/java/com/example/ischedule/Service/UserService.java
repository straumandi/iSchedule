package com.example.ischedule.Service;

import com.example.ischedule.Model.User;
import com.example.ischedule.Model.UserRole;
import com.example.ischedule.security.CustomUserDetails;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User getUserById(int id);

    CustomUserDetails getCurrentUser();

    User saveUser(User user);

    void deleteUser(int id);

    List<User> getUsersByCourseId(int courseId);

    List<User> getUsersByRole(UserRole role);
    List<User> getUsersByRoleIn(UserRole... roles);

    User updateUser(Integer id, User updatedUser);

    /*
     * Eagerly fetch the enrolledCourses collection.
     * This ensures that the User entity retrieved from the database
     * contains the enrolled courses
     * and remains attached to the persistence context.
     */
    @EntityGraph(attributePaths = "enrolledCourses")
    User getUserByUsername(String username);
}


