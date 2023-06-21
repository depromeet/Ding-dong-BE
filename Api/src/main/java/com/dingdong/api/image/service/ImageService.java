package com.dingdong.api.image.service;


import com.dingdong.domain.domains.image.adaptor.ImageAdaptor;
import com.dingdong.domain.domains.image.domain.DeleteImage;
import com.dingdong.infrastructure.image.ImageHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageHandler imageHandler;
    private final ImageAdaptor imageAdaptor;

    public String uploadImage(MultipartFile multipartFile) {
        return imageHandler.uploadImage(multipartFile);
    }

    /** 사용하지 않는 이미지 S3에서 제거 스케쥴러 매일 00:00에 수행 */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteImage() {
        List<DeleteImage> deleteImages = imageAdaptor.findAll();

        for (DeleteImage deleteImage : deleteImages) {
            String imageUrl = deleteImage.getImageUrl();
            /** 순서 보장 되어야 함 */
            imageHandler.removeImage(imageUrl);
            imageAdaptor.delete(deleteImage);
        }
    }
}
