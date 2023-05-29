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

    void updateUser(Integer id, User updatedUser);

    /*
     * Retrieves a user by their username and eagerly fetches the enrolledCourses collection.
     * Improves performance by loading the User entity and enrolledCourses collection in a single query.
     */
    @EntityGraph(attributePaths = "enrolledCourses")
    User getUserByUsername(String username);
}


