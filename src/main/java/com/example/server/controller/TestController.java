package com.example.server.controller;

import com.example.server.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ApiResponse<Map<String, Object>> test() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "API测试成功");
        data.put("timestamp", System.currentTimeMillis());
        return ApiResponse.success("测试接口正常", data);
    }
}