package com.dingdong.infrastructure.s3;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Component
public class S3Api {

    private final AmazonS3 amazonS3;

    public S3Api() {
        this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
    }

    public String uploadImage(
            String bucket,
            String fileName,
            MultipartFile multipartFile,
            ObjectMetadata objectMetadata) {

        String fileUrl = "";
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            fileUrl = amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return fileUrl;
    }

    public void removeImage(String bucket, String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
