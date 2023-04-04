package com.example.application.SiteController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

    @GetMapping("/sport")
    public String sport(Model model) {
        return "sport.html";
    }
        @RequestMapping("/login.html")
        public String login() {
            return "login.html";
        }

        // Login form with error
        @RequestMapping("/login-error.html")
        public String loginError(Model model) {
            model.addAttribute("loginError", true);
            return "login.html";
        }

    }