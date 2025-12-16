package com.example.springBoot_ecommerce_backend.services.image;

import java.util.List;

import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springBoot_ecommerce_backend.Exception.ResourceNotFoundException;
import com.example.springBoot_ecommerce_backend.domains.Image;
import com.example.springBoot_ecommerce_backend.domains.Product;
import com.example.springBoot_ecommerce_backend.dtos.ImageDto;
import com.example.springBoot_ecommerce_backend.repositories.ImageRepository;
import com.example.springBoot_ecommerce_backend.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageInterface {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public Image getImageById(Long id) {

        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("image not found"));
    }

    @Override
    public String deleteImageById(Long id) {

        Image img = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("image not found"));
        imageRepository.delete(img);

        return "image deleted";
    }

    @Override
    public String saveImages(List<MultipartFile> files, Long productId) {

        return mapMultipartfileToImages(files, productId);
    }

    String mapMultipartfileToImages(List<MultipartFile> files, Long ProductId) {

        Product product = productRepository.findById(ProductId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found"));

        for (MultipartFile file : files) {

            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setProduct(product);

            try {

                byte[] bytes = file.getBytes();
                Blob blob = new SerialBlob(bytes);
                image.setImg(blob);
            } catch (Exception e) {
                throw new RuntimeException("failed to create blob");
            }

            image = imageRepository.save(image);

            String url = "api/images/" + image.getId();

            image.setDownloadUrl(url);

            imageRepository.save(image);

        }

        return "images saved";

    }

    @Override
    public ImageDto updateImage(MultipartFile file, Long imageId) {

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("image not found"));

        image.setFileType(file.getContentType());
        image.setFileName(file.getOriginalFilename());

        try {

            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);
            image.setImg(blob);
        } catch (Exception e) {
            throw new RuntimeException("failed to create a blob");
        }

        imageRepository.save(image);

        ImageDto imageDto = new ImageDto();
        imageDto.setDownloadUrl(image.getDownloadUrl());
        imageDto.setFileName(image.getFileName());
        imageDto.setFileType(image.getFileType());

        return imageDto;
    }
}
