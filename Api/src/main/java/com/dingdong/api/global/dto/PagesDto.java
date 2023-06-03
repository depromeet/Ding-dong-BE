package com.dingdong.api.global.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PagesDto {

    @Schema(description = "현재 페이지")
    private Integer page;

    @Schema(description = "페이지 당 데이터 수")
    private Integer size;

    @Schema(description = "마지막 페이지")
    private Integer totalPages;

    @Schema(description = "데이터 총 개수")
    private Long totalElements;

    public PagesDto(Page<?> page) {
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
