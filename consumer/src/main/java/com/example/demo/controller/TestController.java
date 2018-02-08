package com.example.demo.controller;

import cn.com.aperfect.DemoService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    private DemoService demoService;

    @RequestMapping("/test")
    public String test() {
        return demoService.sayHello("dubbo");
    }
}
