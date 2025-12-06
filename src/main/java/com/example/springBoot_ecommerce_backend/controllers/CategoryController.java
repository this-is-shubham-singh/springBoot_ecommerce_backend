package com.example.springBoot_ecommerce_backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.example.springBoot_ecommerce_backend.response.ApiResponse;
import com.example.springBoot_ecommerce_backend.domains.Category;
import com.example.springBoot_ecommerce_backend.dtos.CategoryDto;
import com.example.springBoot_ecommerce_backend.services.category.CategoryInterface;


@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryInterface categoryInterface;

    @GetMapping("/allCategories")
    public ResponseEntity<ApiResponse> getAllCategories() {

        List<Category> categories = categoryInterface.getAllCategories();

        return ResponseEntity.ok(new ApiResponse("list of categories", categories));
    }

    @PostMapping("/addCategory")
    private ResponseEntity<ApiResponse> addCategory(@RequestBody String categoryName) {

        String name = categoryInterface.addCategory(categoryName);

        return ResponseEntity.ok(new ApiResponse("category added", name));
    }

    @GetMapping("/getById/{id}")
    private ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {

        Category category = categoryInterface.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("category", category));
    }

    @GetMapping("/getByName/{name}")
    private ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {

        Category category = categoryInterface.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("category", category));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {

        String message = categoryInterface.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("category deleted", message));
    }

    @PutMapping("/update")
    private ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto category = categoryInterface.updateCategory(categoryDto);
          return ResponseEntity.ok(new ApiResponse("category updated", category));
    }
}
