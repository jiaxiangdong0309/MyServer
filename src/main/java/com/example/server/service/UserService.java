package com.example.server.service;

import com.example.server.common.ResultCode;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.util.Assert;
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
        Assert.notNull(user, "用户信息不能为空");
        Assert.notEmpty(user.getUsername(), "用户名不能为空");
        Assert.notEmpty(user.getPhone(), "手机号不能为空");
        return userRepository.save(user);
    }

    // 根据ID查询用户
    public Optional<User> getUserById(Long id) {
        Assert.notNull(id, "用户ID不能为空");
        return userRepository.findById(id);
    }

    // 根据用户名查询用户
    public Optional<User> getUserByUsername(String username) {
        Assert.notEmpty(username, "用户名不能为空");
        return userRepository.findByUsername(username);
    }

    // 根据手机号查询用户
    public Optional<User> getUserByPhone(String phone) {
        Assert.notEmpty(phone, "手机号不能为空");
        return userRepository.findByPhone(phone);
    }

    // 根据姓名模糊查询用户
    public List<User> searchUsersByName(String name) {
        Assert.notEmpty(name, "查询名称不能为空");
        return userRepository.findByUsernameContaining(name);
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 更新用户信息
    public User updateUser(User user) {
        Assert.notNull(user, "用户信息不能为空");
        Assert.notNull(user.getId(), "用户ID不能为空");
        Assert.notEmpty(user.getUsername(), "用户名不能为空");
        Assert.notEmpty(user.getPhone(), "手机号不能为空");

        // 检查用户是否存在
        Assert.isTrue(userRepository.existsById(user.getId()),
                ResultCode.NOT_FOUND.getCode(), "用户不存在");

        return userRepository.save(user);
    }

    // 删除用户
    public void deleteUser(Long id) {
        Assert.notNull(id, "用户ID不能为空");
        // 检查用户是否存在
        Assert.isTrue(userRepository.existsById(id),
                ResultCode.NOT_FOUND.getCode(), "用户不存在");
        userRepository.deleteById(id);
    }

    // 检查用户名是否存在
    public boolean isUsernameExists(String username) {
        Assert.notEmpty(username, "用户名不能为空");
        return userRepository.existsByUsername(username);
    }

    // 检查手机号是否存在
    public boolean isPhoneExists(String phone) {
        Assert.notEmpty(phone, "手机号不能为空");
        return userRepository.existsByPhone(phone);
    }
}