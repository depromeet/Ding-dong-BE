package com.dingdong.api.global.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class SliceResponse<T> {

    private List<T> content;
    private long page;
    private int size;
    private boolean hasNext;

    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                slice.getNumber(),
                slice.getNumberOfElements(),
                slice.hasNext());
    }
}
