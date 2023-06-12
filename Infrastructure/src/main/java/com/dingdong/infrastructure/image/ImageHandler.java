package com.dingdong.infrastructure.image;


import org.springframework.web.multipart.MultipartFile;

public interface ImageHandler {
    String uploadImage(MultipartFile multipartFile);

    void removeImage(String imageUrl);
}
