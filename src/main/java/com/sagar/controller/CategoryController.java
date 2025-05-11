package com.sagar.controller;

import com.sagar.model.Category;
import com.sagar.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getActiveCategories());
        model.addAttribute("currentPath", "activated");
        return "admin/category";
    }

    @GetMapping("/deactivated")
    public String getDeactivatedCategories(Model model) {
        model.addAttribute("categories", categoryService.getInactiveCategories());
        model.addAttribute("currentPath", "deactivated");
        return "admin/category";
    }

    @GetMapping("/{id}")
    public String getCategory(@PathVariable String id, Model model) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        return "category";
    }

    @PostMapping("/save")
    public String saveCategory(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("image") MultipartFile image) throws IOException {
        categoryService.saveCategory(name, description, image);
        return "redirect:/admin/category";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCategory(@PathVariable("id") String id) {
        categoryService.deactivateCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/activate/{id}")
    public String activateCategory(@PathVariable("id") String id) {
        categoryService.activateCategory(id);
        return "redirect:/admin/category";
    }

    @PostMapping("/update")
    public String updateCategory(@RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("image") MultipartFile image) throws IOException {
        categoryService.updateCategory(id, name, description, image);
        return "redirect:/admin/category";
    }

}
