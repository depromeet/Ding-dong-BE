package com.dingdong.infrastructure.s3;


import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {
    String uploadImage(MultipartFile multipartFile);

    void remove(String fileUrl) throws Exception;
}
