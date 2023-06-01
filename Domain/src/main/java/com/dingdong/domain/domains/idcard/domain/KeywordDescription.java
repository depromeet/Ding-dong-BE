package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_keyword_description")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordDescription extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long keywordId;

    @NotNull private String description;

    private KeywordDescription(Long keywordId, String description) {
        this.keywordId = keywordId;
        this.description = description;
    }

    public static KeywordDescription toEntity(Long keywordId, String description) {
        return new KeywordDescription(keywordId, description);
    }
}
