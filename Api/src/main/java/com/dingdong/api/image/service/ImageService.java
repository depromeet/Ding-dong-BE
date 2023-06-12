package com.dingdong.api.image.service;


import com.dingdong.domain.domains.image.adaptor.ImageAdaptor;
import com.dingdong.domain.domains.image.domain.Image;
import com.dingdong.infrastructure.s3.IS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageAdaptor imageAdaptor;
    private final IS3Service s3Service;

    public String uploadImage(MultipartFile multipartFile) {
        return s3Service.uploadImage(multipartFile);
    }

    public void archive(String imageUrl) {
        imageAdaptor.save(Image.toEntity(imageUrl));
    }
}
