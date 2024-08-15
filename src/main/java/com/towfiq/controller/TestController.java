package com.towfiq.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        return "Test Data by GET mapping.";
    }

    @PostMapping("/post")
    @ResponseBody
    public String post() {
        return "Test Data by POST mapping.";
    }
}
