package com.example.server.controller;

import com.example.server.common.ApiResponse;
import com.example.server.common.ResultCode;
import com.example.server.entity.User;
import com.example.server.exception.ApiException;
import com.example.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 创建用户
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        // 检查用户名和手机号是否已存在
        if (userService.isUsernameExists(user.getUsername())) {
            throw new ApiException(ResultCode.CONFLICT.getCode(), "用户名已存在");
        }
        if (userService.isPhoneExists(user.getPhone())) {
            throw new ApiException(ResultCode.CONFLICT.getCode(), "手机号已存在");
        }
        return ApiResponse.success("用户创建成功", userService.createUser(user));
    }

    // 获取所有用户
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id)
                .orElseThrow(() -> new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在")));
    }

    // 根据用户名获取用户
    @GetMapping("/username/{username}")
    public ApiResponse<User> getUserByUsername(@PathVariable String username) {
        return ApiResponse.success(userService.getUserByUsername(username)
                .orElseThrow(() -> new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在")));
    }

    // 根据姓名模糊查询用户
    @GetMapping("/search")
    public ApiResponse<List<User>> searchUsersByName(@RequestParam String name) {
        List<User> users = userService.searchUsersByName(name);
        if (users.isEmpty()) {
            throw new ApiException(ResultCode.NOT_FOUND.getCode(), "未找到匹配的用户");
        }
        return ApiResponse.success(users);
    }

    // 根据手机号获取用户
    @GetMapping("/phone/{phone}")
    public ApiResponse<User> getUserByPhone(@PathVariable String phone) {
        return ApiResponse.success(userService.getUserByPhone(phone)
                .orElseThrow(() -> new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在")));
    }

    // 更新用户
    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return ApiResponse.success("用户更新成功", userService.updateUser(user));
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success("用户删除成功", null);
    }
}