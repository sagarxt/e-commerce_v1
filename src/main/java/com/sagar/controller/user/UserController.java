package com.sagar.controller.user;

import com.sagar.model.User;
import com.sagar.service.CartService;
import com.sagar.service.CategoryService;
import com.sagar.service.ProductService;
import com.sagar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String user(Model model) {
        User user = userService.getLoggedInUser();
        if(user == null) {
            return "redirect:/login";
        }

        model.addAttribute("userId", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("cartCount", cartService.getCart(user.getId()).getItems().size());
        model.addAttribute("products", productService.getRandomProducts(10)); // limit 10 random
        model.addAttribute("categories", categoryService.getCategories());

        return "user/home";
    }
}
