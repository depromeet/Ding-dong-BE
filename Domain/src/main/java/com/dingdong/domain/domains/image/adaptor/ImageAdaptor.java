package com.dingdong.domain.domains.image.adaptor;


import com.dingdong.core.annotation.Adaptor;
import com.dingdong.domain.domains.image.domain.DeleteImage;
import com.dingdong.domain.domains.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ImageAdaptor {
    private final ImageRepository imageRepository;

    public void save(DeleteImage deleteImage) {
        imageRepository.save(deleteImage);
    }
}
