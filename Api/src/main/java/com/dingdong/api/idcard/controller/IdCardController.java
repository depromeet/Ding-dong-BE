package com.dingdong.api.idcard.controller;


import com.dingdong.api.global.response.IdResponse;
import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.controller.response.CommentCountResponse;
import com.dingdong.api.idcard.controller.response.IdCardDetailsResponse;
import com.dingdong.api.idcard.service.IdCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "주민증")
@RestController
@RequestMapping("/id-cards")
@RequiredArgsConstructor
public class IdCardController {

    private final IdCardService idCardService;

    @Operation(summary = "주민증 세부 조회")
    @GetMapping("/{idCardsId}")
    public IdCardDetailsResponse getIdCardDetails(@PathVariable Long idCardsId) {
        return IdCardDetailsResponse.from(idCardService.getIdCardDetails(idCardsId));
    }

    @Operation(summary = "댓글 갯수 조회")
    @GetMapping("/{idCardsId}/comment-count")
    public CommentCountResponse getCommentCount(@PathVariable Long idCardsId) {
        return new CommentCountResponse();
    }

    @Operation(summary = "주민증 생성")
    @PostMapping
    public IdResponse postIdCard(@RequestBody @Valid CreateIdCardRequest body) {
        return IdResponse.from(idCardService.createIdCard(body));
    }

    @Operation(summary = "주민증 수정", description = "주민증 수정 정보를 받아와 put 요청을 통해 덮어씁니다.")
    @PutMapping("/{idCardsId}")
    public IdResponse putIdCard(
            @PathVariable Long idCardsId, @RequestBody @Valid CreateIdCardRequest body) {
        return IdResponse.from(1L);
    }
}
