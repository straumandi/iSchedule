package com.example.ischedule.Service;

import com.example.ischedule.Model.User;
import com.example.ischedule.Model.UserRole;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User getUserById(int id);

    User saveUser(User user);

    void deleteUser(int id);

    List<User> getUsersByCourseId(int courseId);

    List<User> getUsersByRole(UserRole role);

    User updateUser(Integer id, User updatedUser);
}


