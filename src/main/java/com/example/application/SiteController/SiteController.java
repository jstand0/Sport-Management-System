package com.example.application.SiteController;

import com.example.application.User.User;
import com.example.application.UserDto.UserDto;
import com.example.application.UserRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SiteController {

    private UserRepository userRepository;

    @GetMapping("/index")
    public String home() {
        return "index.html";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login.html";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register.html";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userRepository.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register.html";
        }
//        userRepository.save(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users.html";
    }
}
