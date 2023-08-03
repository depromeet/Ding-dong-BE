package com.dingdong.api.idcard.controller;


import com.dingdong.api.global.response.IdResponse;
import com.dingdong.api.global.response.SliceResponse;
import com.dingdong.api.idcard.controller.request.CreateCommentRequest;
import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.controller.request.UpdateIdCardRequest;
import com.dingdong.api.idcard.controller.response.CommentCountResponse;
import com.dingdong.api.idcard.controller.response.CommentRepliesResponse;
import com.dingdong.api.idcard.controller.response.IdCardDetailsResponse;
import com.dingdong.api.idcard.dto.CommentDto;
import com.dingdong.api.idcard.service.CommentService;
import com.dingdong.api.idcard.service.IdCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final CommentService commentService;

    @Operation(summary = "주민증 세부 조회")
    @GetMapping("/{idCardsId}")
    public IdCardDetailsResponse getIdCardDetails(@PathVariable Long idCardsId) {
        return IdCardDetailsResponse.from(idCardService.getIdCardDetails(idCardsId));
    }

    @Operation(summary = "댓글 갯수 조회")
    @GetMapping("/{idCardsId}/comment-count")
    public CommentCountResponse getCommentCount(@PathVariable Long idCardsId) {
        return CommentCountResponse.from(idCardService.getCommentCount(idCardsId));
    }

    @Operation(summary = "주민증 생성")
    @PostMapping
    public IdResponse postIdCard(@RequestBody @Valid CreateIdCardRequest body) {
        return IdResponse.from(idCardService.createIdCard(body));
    }

    @Operation(summary = "주민증 수정", description = "주민증 수정 정보를 받아와 put 요청을 통해 덮어씁니다.")
    @PutMapping("/{idCardsId}")
    public IdResponse putIdCard(
            @PathVariable Long idCardsId, @RequestBody @Valid UpdateIdCardRequest body) {
        return IdResponse.from(idCardService.updateIdCard(idCardsId, body));
    }

    @Operation(summary = "주민증 댓글 달기")
    @PostMapping("/{idCardsId}/comments")
    public IdResponse postComment(
            @PathVariable Long idCardsId, @RequestBody @Valid CreateCommentRequest body) {
        return IdResponse.from(commentService.createComment(idCardsId, body));
    }

    @Operation(summary = "주민증 대댓글 달기")
    @PostMapping("/{idCardsId}/comments/{commentId}/replies")
    public IdResponse postCommentReply(
            @PathVariable Long idCardsId,
            @PathVariable Long commentId,
            @RequestBody @Valid CreateCommentRequest body) {
        return IdResponse.from(commentService.createCommentReply(idCardsId, commentId, body));
    }

    @Operation(summary = "주민증 댓글 조회")
    @GetMapping("/{idCardsId}/comments")
    public SliceResponse<CommentDto> getComments(
            @PathVariable Long idCardsId, @PageableDefault Pageable pageable) {
        return SliceResponse.from(commentService.getComments(idCardsId, pageable));
    }

    @Operation(summary = "주민증 대댓글 조회")
    @GetMapping("/{idCardsId}/comments/{commentId}/replies")
    public CommentRepliesResponse getReplies(
            @PathVariable Long idCardsId, @PathVariable Long commentId) {
        return CommentRepliesResponse.of(
                commentId, commentService.getReplies(idCardsId, commentId));
    }

    @Operation(summary = "주민증 댓글 좋아요")
    @PostMapping("/{idCardsId}/comments/{commentId}/likes")
    public void postCommentLike(@PathVariable Long idCardsId, @PathVariable Long commentId) {
        commentService.createCommentLike(idCardsId, commentId);
    }

    @Operation(summary = "주민증 댓글 삭제")
    @DeleteMapping("/{idCardsId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long idCardsId, @PathVariable Long commentId) {
        commentService.deleteComment(idCardsId, commentId);
    }

    @Operation(summary = "주민증 댓글 좋아요 취소")
    @DeleteMapping("/{idCardsId}/comments/{commentId}/likes")
    public void deleteCommentLike(@PathVariable Long idCardsId, @PathVariable Long commentId) {
        commentService.deleteCommentLike(idCardsId, commentId);
    }
}
