package com.dingdong.domain.domains.image.adaptor;


import com.dingdong.core.annotation.Adaptor;
import com.dingdong.domain.domains.image.domain.DeleteImage;
import com.dingdong.domain.domains.image.repository.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ImageAdaptor {
    private final ImageRepository imageRepository;

    public void save(DeleteImage deleteImage) {
        imageRepository.save(deleteImage);
    }

    public void saveAll(List<DeleteImage> deleteImages) {
        imageRepository.saveAll(deleteImages);
    }

    public List<DeleteImage> findAll() {
        return imageRepository.findAll();
    }

    public void delete(DeleteImage deleteImage) {
        imageRepository.delete(deleteImage);
    }
}
