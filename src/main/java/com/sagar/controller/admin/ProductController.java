package com.sagar.controller.admin;

import com.sagar.service.CategoryService;
import com.sagar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProductDTOs());
        model.addAttribute("categories", categoryService.getActiveCategories());
        model.addAttribute("currentPath", "activated");
        return "admin/product";
    }

    @GetMapping("/deactivated")
    public String getDeactivatedProducts(Model model) {
        model.addAttribute("products", productService.getDeactivatedProductDTOs());
        model.addAttribute("categories", categoryService.getActiveCategories());
        model.addAttribute("currentPath", "deactivated");
        return "admin/product";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("image") MultipartFile image,
                              @RequestParam("categoryId") String categoryId,
                              @RequestParam("MRP") Double MRP,
                              @RequestParam("price") Double price) throws IOException {
        productService.saveProduct(name, description, image, categoryId, MRP, price);
        return "redirect:/admin/product";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateProduct(@PathVariable("id") String id) {
        productService.deactivateProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/activate/{id}")
    public String activateProduct(@PathVariable("id") String id) {
        productService.activateProduct(id);
        return "redirect:/admin/product";
    }
}
