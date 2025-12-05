package com.example.springBoot_ecommerce_backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    public String fileName;
    public String fileType;
    public String downloadUrl;
}
