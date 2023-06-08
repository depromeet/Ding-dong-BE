package com.dingdong.infrastructure.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    private final AmazonS3 amazonS3;

    public String uploadImage(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        String fileUrl = "";
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return fileUrl;
    }

    public List<String> uploadImageList(List<MultipartFile> multipartFile) {

        List<String> fileUrlList = new ArrayList<>();

        multipartFile.forEach(
                file -> {
                    String fileName = createFileName(file.getOriginalFilename());
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(file.getSize());
                    objectMetadata.setContentType(file.getContentType());

                    try (InputStream inputStream = file.getInputStream()) {
                        amazonS3.putObject(
                                new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                                        .withCannedAcl(CannedAccessControlList.PublicRead));
                        String fileUrl = amazonS3.getUrl(bucket, fileName).toString();
                        fileUrlList.add(fileUrl);
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                });

        return fileUrlList;
    }

    public void remove(String fileUrl) throws Exception {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 확장자 전달
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 (" + fileName + ") 입니다.");
        }
    }
}
