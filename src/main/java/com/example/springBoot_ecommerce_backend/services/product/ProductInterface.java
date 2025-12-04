package com.example.springBoot_ecommerce_backend.services.product;

import com.example.springBoot_ecommerce_backend.dtos.ProductDto;
import java.util.List;

public interface ProductInterface {
    public ProductDto addProduct(ProductDto productDto);
    public ProductDto getProductById(Long id);
    public String deleteProductById(Long id);
    public ProductDto updateProduct(ProductDto productDto, Long id);
    public List<ProductDto> getAllProducts();
    public List<ProductDto> getProductByCategory(String categoryName);
    public List<ProductDto> getProductByBrand(String brandName);
    public List<ProductDto> getProductByCategoryAndBrand(String categoryName, String brandName);
    public ProductDto getProductByName(String productName);
    public ProductDto getProductByBrandAndName(String brandName, String prodctName);
    public int countProductByBrandAndName(String brandName, String productName);
}
