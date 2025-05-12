package com.sagar.service;

import com.sagar.model.Category;
import com.sagar.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    public Category saveCategory(String name, String description, MultipartFile image) throws IOException {
        String imageId = imageService.saveImage(image);

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setImageId(imageId);
        category.setActive(true);

        return categoryRepository.save(category);
    }

    public Category getCategory(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByActive(true);
    }

    public List<Category> getInactiveCategories() {
        return categoryRepository.findByActive(false);
    }

    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            categoryRepository.delete(category);
        }
    }

    public void deactivateCategory(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            category.setActive(false);
            categoryRepository.save(category);
        }
    }

    public void activateCategory(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            category.setActive(true);
            categoryRepository.save(category);
        }
    }

    public void updateCategory(String id, String name, String description, MultipartFile image) throws IOException {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            category.setName(name);
            category.setDescription(description);
            if(!image.isEmpty()) {
                String imageId = imageService.saveImage(image);
                category.setImageId(imageId);
            }
            categoryRepository.save(category);
        }
    }
}
