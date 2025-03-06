package com.example.server.service;

import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 创建用户
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 根据ID查询用户
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 根据用户名查询用户
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 根据手机号查询用户
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    // 根据姓名模糊查询用户
    public List<User> searchUsersByName(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 更新用户信息
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // 删除用户
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 检查用户名是否存在
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // 检查手机号是否存在
    public boolean isPhoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }
}