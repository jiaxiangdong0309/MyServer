package com.example.server.controller;

import com.example.server.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hehe")
public class TestController {

    @GetMapping
    public ApiResponse<Map<String, Object>> test() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "API测试成功");
        data.put("timestamp", System.currentTimeMillis());
        return ApiResponse.success("测试接口正常", data);
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestParam String phone) {
        Map<String, String> data = new HashMap<>();
        data.put("phone", phone);
        return ApiResponse.success("登录成功", data);
    }

    @GetMapping("/resumes")
    public ApiResponse<List<Map<String, String>>> getResumes() {
        List<Map<String, String>> resumes = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Map<String, String> resume = new HashMap<>();
            resume.put("id", String.valueOf(i));
            resume.put("name", "我的简历" + i);
            resume.put("createTime", String.valueOf(System.currentTimeMillis()));
            resumes.add(resume);
        }

        return ApiResponse.success("获取历史简历成功", resumes);
    }

    @GetMapping("/jobs")
    public ApiResponse<List<Map<String, Object>>> getJobs() {
        List<Map<String, Object>> jobs = new ArrayList<>();

        String[] companies = {"字节跳动", "阿里巴巴", "腾讯", "百度", "美团", "京东", "华为", "小米", "网易", "滴滴"};
        String[] locations = {"北京 · 朝阳区", "杭州 · 西湖区", "深圳 · 南山区", "上海 · 浦东新区", "广州 · 天河区"};
        String[] salaryRanges = {"30K-50K", "25K-45K", "35K-55K", "40K-60K", "45K-65K"};
        String[] experiences = {"1-3年", "3-5年", "5-7年", "应届生"};
        String[] educations = {"本科", "硕士", "博士"};
        String[] postTimes = {"3小时前", "5小时前", "1天前", "2天前", "3天前"};

        for (int i = 1; i <= 10; i++) {
            Map<String, Object> job = new HashMap<>();
            job.put("id", String.valueOf(i));
            job.put("title", "前端开发工程师");
            job.put("company", companies[i - 1]);
            job.put("location", locations[i % locations.length]);
            job.put("salary", salaryRanges[i % salaryRanges.length]);
            job.put("tags", Arrays.asList(
                experiences[i % experiences.length],
                educations[i % educations.length],
                "前端开发"
            ));
            job.put("requirement", "精通React/Vue等前端框架，有小程序开发经验优先");
            job.put("posted", postTimes[i % postTimes.length]);
            job.put("isNew", i <= 3); // 前3个标记为新职位

            jobs.add(job);
        }

        return ApiResponse.success("获取职位列表成功", jobs);
    }


}