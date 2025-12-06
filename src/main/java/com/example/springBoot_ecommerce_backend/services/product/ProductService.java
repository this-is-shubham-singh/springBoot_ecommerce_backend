package com.example.springBoot_ecommerce_backend.services.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springBoot_ecommerce_backend.Exception.AlreadyExistsException;
import com.example.springBoot_ecommerce_backend.Exception.ResourceNotFoundException;
import com.example.springBoot_ecommerce_backend.domains.Product;
import com.example.springBoot_ecommerce_backend.dtos.ProductDto;
import com.example.springBoot_ecommerce_backend.repositories.CategoryRepository;
import com.example.springBoot_ecommerce_backend.repositories.ProductRepository;
import com.example.springBoot_ecommerce_backend.domains.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInterface {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {

        Optional<Product> existingProduct = productRepository.findByBrandAndName(productDto.getBrandName(),
                productDto.getName());
        if (existingProduct.isPresent()) {
            throw new AlreadyExistsException("product already exist");
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setInventory(productDto.getInventory());
        product.setBrand(productDto.getBrandName());

        Category existingCategory = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));

        product.setCategory(existingCategory);
        productRepository.save(product);

        ProductDto newProductDto = new ProductDto();
        newProductDto.setName(product.getName());
        newProductDto.setDescription(product.getDescription());
        newProductDto.setPrice(product.getPrice());
        newProductDto.setCategoryId(product.getCategory().getId());
        newProductDto.setBrandName(product.getBrand());
        newProductDto.setInventory(product.getInventory());

        return newProductDto;

    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));

        ProductDto newProductDto = new ProductDto();
        newProductDto.setName(product.getName());
        newProductDto.setDescription(product.getDescription());
        newProductDto.setPrice(product.getPrice());
        newProductDto.setCategoryId(product.getCategory().getId());
        newProductDto.setBrandName(product.getBrand());
        newProductDto.setInventory(product.getInventory());

        return newProductDto;
    }

    @Override
    public String deleteProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found"));
        productRepository.delete(product);

        return "product deleted";
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {

        
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));

        if(productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if(productDto.getBrandName() != null) {
            product.setBrand(productDto.getBrandName());
        }
        if(productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if(productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if(productDto.getCategoryId() != null) {

            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category not found"));
            product.setCategory(category);
        }
        if(productDto.getInventory() != 0) {
            product.setInventory(productDto.getInventory());
        }

        productRepository.save(product);

        ProductDto newProductDto = new ProductDto();
        newProductDto.setName(product.getName());
        newProductDto.setDescription(product.getDescription());
        newProductDto.setPrice(product.getPrice());
        newProductDto.setCategoryId(product.getCategory().getId());
        newProductDto.setBrandName(product.getBrand());
        newProductDto.setInventory(product.getInventory());

        return newProductDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> listOfProducts = mapProductToDto(products);
        
        return listOfProducts;

    }

    List<ProductDto> mapProductToDto(List<Product> products) {

        List<ProductDto> listOfProductDtos = new ArrayList<>();

        if(products.isEmpty()) {
            return listOfProductDtos;
        }

        for(Product product : products) {
            
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setBrandName(product.getBrand());
            productDto.setInventory(product.getInventory());

            listOfProductDtos.add(productDto);
        }

        return listOfProductDtos;
    }

    @Override
    public List<ProductDto> getProductByCategory(String categoryName) {

        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDto> listOfProducts = mapProductToDto(products);
        
        return listOfProducts;

    }

    @Override
    public List<ProductDto> getProductByBrand(String brandName) {
        
        List<Product> products = productRepository.findByBrand(brandName);
        List<ProductDto> listOfProducts = mapProductToDto(products);
        
        return listOfProducts;
    }

    @Override
    public List<ProductDto> getProductByCategoryAndBrand(String categoryName, String brandName) {

        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        List<Product> products = productRepository.findByCategoryAndBrand(category, brandName);
        List<ProductDto> listOfProducts = mapProductToDto(products);

        return listOfProducts;
    }

    @Override
    public List<ProductDto> getProductByName(String productName) {

        List<Product> products = productRepository.findByName(productName);
        return mapProductToDto(products);
    }

    @Override
    public ProductDto getProductByBrandAndName(String brandName, String productName) {
        Product product = productRepository.findByNameAndBrand(productName, brandName).orElseThrow(() -> new ResourceNotFoundException("product not found"));
   
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setBrandName(product.getBrand());
        productDto.setInventory(product.getInventory());

        return productDto;
    }

    @Override
    public int countProductByBrandAndName(String brandName, String productName) {

        return productRepository.countByBrandAndName(brandName, productName);
    }
}
