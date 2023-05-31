package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_id_card")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdCard extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded private UserInfo userInfo;
}
