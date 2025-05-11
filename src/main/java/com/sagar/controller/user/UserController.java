package com.sagar.controller.user;

import com.sagar.model.User;
import com.sagar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String user(Model model) {
        User user = userService.getLoggedInUser();
        System.out.println("User Dashboard of : " + user.getEmail());
        model.addAttribute("user", user);
        return "user/home";
    }
}
