package com.dingdong.api.image.service;


import com.dingdong.infrastructure.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageHandler imageHandler;

    public String uploadImage(MultipartFile multipartFile) {
        return imageHandler.uploadImage(multipartFile);
    }

    public void removeImage(String imageUrl) {
        imageHandler.removeImage(imageUrl);
    }
}
