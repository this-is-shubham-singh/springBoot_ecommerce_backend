package com.example.springBoot_ecommerce_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.springBoot_ecommerce_backend.dtos.ProductDto;
import com.example.springBoot_ecommerce_backend.response.ApiResponse;
import com.example.springBoot_ecommerce_backend.services.product.ProductInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductInterface productInterface;

    @PostMapping("/addProduct")
    ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {

        ProductDto newProductDto = productInterface.addProduct(productDto);
        return ResponseEntity.ok(new ApiResponse("product added", newProductDto));
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<ApiResponse> getAllProducts() {

        List<ProductDto> products = productInterface.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("all products", products));
    }

    @GetMapping("/getById/{id}")
    ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {

        ProductDto productDto = productInterface.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("product by id", productDto));
    }


    @PutMapping("/update/{id}")
    ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
    
        ProductDto newProductDto = productInterface.updateProduct(productDto, id);
        return ResponseEntity.ok(new ApiResponse("product updated", newProductDto));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {

        String response = productInterface.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse("product deleted", response));
    }

    @GetMapping("/getByBrandAndName")
    ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
         
        ProductDto product = productInterface.getProductByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse("product by brand and name", product));
    }

    @GetMapping("/getByName")
    ResponseEntity<ApiResponse> getProductByName(@RequestParam String name) {

        List<ProductDto> products = productInterface.getProductByName(name);
        return ResponseEntity.ok(new ApiResponse("product by name", products));
    }

    @GetMapping("/getByBrand")
    ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        
        List<ProductDto> products = productInterface.getProductByBrand(brand);
        return ResponseEntity.ok(new ApiResponse("products by brand", products));
    }
}
