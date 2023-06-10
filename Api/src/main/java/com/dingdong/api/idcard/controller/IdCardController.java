package com.dingdong.api.idcard.controller;


import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.controller.response.CommentCountResponse;
import com.dingdong.api.idcard.controller.response.IdCardDetailsResponse;
import com.dingdong.api.idcard.service.CreateIdCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "주민증")
@RestController
@RequestMapping("/id-cards")
@RequiredArgsConstructor
public class IdCardController {

    private final CreateIdCardService createIdCardService;

    @Operation(summary = "주민증 세부 조회")
    @GetMapping("/{idCardsId}")
    public IdCardDetailsResponse getIdCardDetails(@PathVariable Long idCardsId) {
        return new IdCardDetailsResponse();
    }

    @Operation(summary = "댓글 갯수 조회")
    @GetMapping("/{idCardsId}/comment-count")
    public CommentCountResponse getCommentCount(@PathVariable Long idCardsId) {
        return new CommentCountResponse();
    }

    @Operation(summary = "주민증 생성")
    @PostMapping
    public void postIdCard(@RequestBody @Valid CreateIdCardRequest body) {
        createIdCardService.execute(body);
    }
}
