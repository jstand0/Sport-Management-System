package com.example.application.rest_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public  class RestController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }
}