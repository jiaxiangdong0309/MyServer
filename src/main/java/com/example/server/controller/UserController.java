package com.example.server.controller;

import com.example.server.common.ApiResponse;
import com.example.server.common.ResultCode;
import com.example.server.entity.User;
import com.example.server.exception.ApiException;
import com.example.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // 创建用户
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        logger.info("创建用户请求: {}", user);

        // 检查用户名和手机号是否已存在
        if (userService.isUsernameExists(user.getUsername())) {
            logger.warn("用户名已存在: {}", user.getUsername());
            throw new ApiException(ResultCode.CONFLICT.getCode(), "用户名已存在");
        }
        if (userService.isPhoneExists(user.getPhone())) {
            logger.warn("手机号已存在: {}", user.getPhone());
            throw new ApiException(ResultCode.CONFLICT.getCode(), "手机号已存在");
        }

        User createdUser = userService.createUser(user);
        logger.info("用户创建成功: {}", createdUser);
        return createNoCacheResponse(ApiResponse.success("用户创建成功", createdUser));
    }

    // 获取所有用户
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        logger.info("获取所有用户请求");
        List<User> users = userService.getAllUsers();
        logger.info("获取到 {} 个用户", users.size());
        return createNoCacheResponse(ApiResponse.success(users));
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        logger.info("根据ID获取用户请求: {}", id);
        User user = userService.getUserById(id)
                .orElseThrow(() -> {
                    logger.warn("用户不存在: {}", id);
                    return new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
                });
        logger.info("获取到用户: {}", user);
        return createNoCacheResponse(ApiResponse.success(user));
    }

    // 根据用户名获取用户
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<User>> getUserByUsername(@PathVariable String username) {
        logger.info("根据用户名获取用户请求: {}", username);
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("用户不存在: {}", username);
                    return new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
                });
        logger.info("获取到用户: {}", user);
        return createNoCacheResponse(ApiResponse.success(user));
    }

    // 根据姓名模糊查询用户
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsersByName(@RequestParam String name) {
        logger.info("搜索用户请求: {}", name);
        List<User> users = userService.searchUsersByName(name);
        if (users.isEmpty()) {
            logger.warn("未找到匹配的用户: {}", name);
            throw new ApiException(ResultCode.NOT_FOUND.getCode(), "未找到匹配的用户");
        }
        logger.info("找到 {} 个匹配的用户", users.size());
        return createNoCacheResponse(ApiResponse.success(users));
    }

    // 根据手机号获取用户
    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponse<User>> getUserByPhone(@PathVariable String phone) {
        logger.info("根据手机号获取用户请求: {}", phone);
        User user = userService.getUserByPhone(phone)
                .orElseThrow(() -> {
                    logger.warn("用户不存在: {}", phone);
                    return new ApiException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
                });
        logger.info("获取到用户: {}", user);
        return createNoCacheResponse(ApiResponse.success(user));
    }

    // 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("更新用户请求: id={}, user={}", id, user);
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        logger.info("用户更新成功: {}", updatedUser);
        return createNoCacheResponse(ApiResponse.success("用户更新成功", updatedUser));
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        logger.info("删除用户请求: {}", id);
        userService.deleteUser(id);
        logger.info("用户删除成功: {}", id);
        return createNoCacheResponse(ApiResponse.success("用户删除成功", null));
    }

    // 创建禁用缓存的响应
    private <T> ResponseEntity<T> createNoCacheResponse(T body) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache().noStore().mustRevalidate())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .header("X-Timestamp", String.valueOf(System.currentTimeMillis()))
                .header("Vary", "*")
                .body(body);
    }
}