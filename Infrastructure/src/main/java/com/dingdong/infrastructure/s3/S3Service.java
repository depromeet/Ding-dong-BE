package com.dingdong.infrastructure.s3;


import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Api s3Api;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = createObjectMetaData(multipartFile);

        return s3Api.uploadImage(bucket, fileName, multipartFile, objectMetadata);
    }

    public void remove(String fileUrl) throws Exception {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        s3Api.removeImage(bucket, fileName);
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 (" + fileName + ") 입니다.");
        }
    }

    private ObjectMetadata createObjectMetaData(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }
}
