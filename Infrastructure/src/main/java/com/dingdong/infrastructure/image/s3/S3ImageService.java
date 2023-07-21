package com.dingdong.infrastructure.image.s3;


import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dingdong.infrastructure.image.ImageHandler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ImageService implements ImageHandler {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Api s3Api;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = createObjectMetaData(multipartFile);

        return s3Api.uploadImage(bucket, fileName, multipartFile, objectMetadata);
    }

    @Async("threadPoolTaskExecutor")
    public void removeImage(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        try {
            s3Api.removeImage(bucket, fileName);
        } catch (RuntimeException e) {
            log.error("imageUrl: {}, errorMessage: {}", imageUrl, e.getMessage());
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, String.format("잘못된 형식의 파일(%s) 입니다.", fileName));
        }
    }

    private ObjectMetadata createObjectMetaData(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }
}
