package com.example.springBoot_ecommerce_backend.services.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springBoot_ecommerce_backend.Exception.AlreadyExistsException;
import com.example.springBoot_ecommerce_backend.Exception.ResourceNotFoundException;
import com.example.springBoot_ecommerce_backend.domains.Category;
import com.example.springBoot_ecommerce_backend.dtos.CategoryDto;
import com.example.springBoot_ecommerce_backend.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryInterface {

    public final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String addCategory(String categoryName) {

        // check if categoryName already exists in db
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);

        if (existingCategory.isPresent()) {
            throw new AlreadyExistsException("category already exists");
        }

        Category newCategory = new Category();
        newCategory.setName(categoryName);

        return categoryRepository.save(newCategory).getName();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        Category existingCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new ResourceNotFoundException("category dosen't exist"));

        existingCategory.setName(categoryDto.getCategoryName());
        categoryRepository.save(existingCategory);

        CategoryDto newCategoryDto = new CategoryDto();
        newCategoryDto.setId(existingCategory.getId());
        newCategoryDto.setCategoryName(existingCategory.getName());

        return newCategoryDto;
    }

    @Override
    public String deleteCategoryById(Long id) {

        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category dosen't exist"));
        categoryRepository.deleteById(id);

        return "category deleted";
    }
}
