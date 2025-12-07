package com.example.springBoot_ecommerce_backend.services.image;

import com.example.springBoot_ecommerce_backend.domains.Image;
import com.example.springBoot_ecommerce_backend.dtos.ImageDto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageInterface {
    public Image getImageById(Long id);
    public String deleteImageById(Long id);
    public String saveImages(List<MultipartFile>  files, Long productId);
    public ImageDto updateImage(MultipartFile file, Long imageId);
}
