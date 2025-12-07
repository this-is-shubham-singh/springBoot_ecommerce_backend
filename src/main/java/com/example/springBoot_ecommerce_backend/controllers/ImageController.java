package com.example.springBoot_ecommerce_backend.controllers;

import java.net.http.HttpHeaders;
import java.sql.Blob;
import java.util.List;

import org.springframework.core.io.Resource;
import org.apache.catalina.connector.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.springBoot_ecommerce_backend.domains.Image;
import com.example.springBoot_ecommerce_backend.dtos.ImageDto;
import com.example.springBoot_ecommerce_backend.response.ApiResponse;
import com.example.springBoot_ecommerce_backend.services.image.ImageInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageInterface imageInterface;

    @PostMapping("/saveImages/{productId}")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam("images") List<MultipartFile> files, @PathVariable Long productId) {

        String response = imageInterface.saveImages(files, productId);
        return ResponseEntity.ok(new ApiResponse("images saved", response));
    }


    @GetMapping("/download/{id}")
    ResponseEntity<Resource> downloadImage(@PathVariable Long id) {

        Image image = imageInterface.getImageById(id);

        try {

            int imageLength = (int)image.getImg().length();
            
            // converting bytes to a type which can be returned to browser 
            ByteArrayResource resource = new ByteArrayResource(image.getImg().getBytes(1, imageLength));
            
            // tells browser, what kind of a file it is  
            // forces browser to download instead of opening 
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header("Content-Disposition", "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }   

    @PutMapping("/update/{imageId}")
    ResponseEntity<ApiResponse> updateImage(@RequestParam("image") MultipartFile file, @PathVariable Long imageId) {

        ImageDto image = imageInterface.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse("image updated", image));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id) {

        String response = imageInterface.deleteImageById(id);
        return ResponseEntity.ok(new ApiResponse("image deleted", response));
    } 
}
