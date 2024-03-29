package com.example.ischedule.Service.impl;

import com.example.ischedule.Model.User;
import com.example.ischedule.Model.UserRole;
import com.example.ischedule.Repository.UserRepository;
import com.example.ischedule.Service.UserService;
import com.example.ischedule.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByCourseId(int courseId) {
        return userRepository.findByCourseId(courseId);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getUsersByRoleIn(UserRole... roles) {
        List<UserRole> roleList = Arrays.asList(roles);
        return userRepository.getUsersByRoleIn(roleList);
    }

    @Override
    public void updateUser(Integer id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return;
        }
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

