package com.dingdong.domain.common.util;


import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@UtilityClass
public class SliceUtil {

    // 리스트를 슬라이스로 변환(비즈니스 로직)
    public static <T> Slice<T> createSliceWithPageable(List<T> contents, Pageable pageable) {
        boolean hasNext = hasNext(contents, pageable);
        return new SliceImpl<>(
                hasNext ? getContent(contents, pageable) : contents, pageable, hasNext);
    }

    // 리스트를 슬라이스로 변환(쿼리)
    public static <T> Slice<T> createSliceWithoutPageable(List<T> contents) {
        return new SliceImpl<>(contents);
    }

    // 다음 페이지 있는지 확인
    private static <T> boolean hasNext(List<T> content, Pageable pageable) {
        return pageable.isPaged() && content.size() > pageable.getPageSize();
    }

    // 데이터 1개 빼고 반환
    private static <T> List<T> getContent(List<T> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }
}
