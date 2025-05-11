package com.sagar.service;

import com.sagar.model.Product;
import com.sagar.model.dtos.ProductDTO;
import com.sagar.repository.CategoryRepository;
import com.sagar.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Product saveProduct(String name, String description, MultipartFile image, String categoryId, Double MRP, Double price) throws IOException {

        String imageUrl = fileStorageService.saveFile(image, name, "product");

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setCategoryId(categoryId);
        product.setMRP(MRP);
        product.setPrice(price);
        product.setActive(true);

        return productRepository.save(product);
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<ProductDTO> getProductDTOs() {
        List<Product> products = productRepository.findByActive(true);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : products) {
            ProductDTO productDTO = new ProductDTO(product);
            productDTO.setCategory(categoryRepository.findById(product.getCategoryId()).get().getName());
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    public List<ProductDTO> getDeactivatedProductDTOs() {
        List<Product> products = productRepository.findByActive(false);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : products) {
            ProductDTO productDTO = new ProductDTO(product);
            productDTO.setCategory(categoryRepository.findById(product.getCategoryId()).get().getName());
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }


    public void deactivateProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null) {
            product.setActive(false);
            productRepository.save(product);
        }
    }

    public void activateProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null) {
            product.setActive(true);
            productRepository.save(product);
        }
    }
}
