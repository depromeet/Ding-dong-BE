package com.dingdong.api.image.controller;


import com.dingdong.api.image.controller.request.DeleteImageRequest;
import com.dingdong.api.image.controller.response.UploadImageResponse;
import com.dingdong.api.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이미지 업로드")
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "이미지 업로드")
    @PostMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadImageResponse uploadImage(@RequestParam("image") MultipartFile multipartFile)
            throws Exception {
        return UploadImageResponse.from(imageService.uploadImage(multipartFile));
    }

    @Operation(summary = "이미지 삭제")
    @DeleteMapping("/image")
    public void remove(@RequestBody DeleteImageRequest request) throws Exception {
        imageService.archive(request.getImageUrl());
    }
}
