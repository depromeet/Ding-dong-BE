package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface IdCardRepositoryExtension {
    Slice<IdCard> findIdCardByConditionInPage(Long communityId, Pageable pageable);
}
