package com.example.springBoot_ecommerce_backend.services.category;

import java.util.List;

import com.example.springBoot_ecommerce_backend.domains.Category;
import com.example.springBoot_ecommerce_backend.dtos.CategoryDto;

public interface CategoryInterface {
    public Category getCategoryById(Long id);
    public Category getCategoryByName(String name);
    public List<Category> getAllCategories();
    public String addCategory(String categoryName);
    public CategoryDto updateCategory(String categoryName, Long id);
    public String deleteCategoryById(Long id);
}
