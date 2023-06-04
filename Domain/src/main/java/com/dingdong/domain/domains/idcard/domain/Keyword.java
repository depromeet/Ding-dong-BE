package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_keyword")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_card_id")
    private IdCard idCard;

    @NotNull private String title;

    @NotNull private String content;

    private String imagerUrl;

    private Keyword(String title, String content, IdCard idCard, String imagerUrl) {
        this.title = title;
        this.content = content;
        this.idCard = idCard;
        this.imagerUrl = imagerUrl;
    }

    public static Keyword toEntity(String title, String content, IdCard idCard, String imagerUrl) {
        return new Keyword(title, content, idCard, imagerUrl);
    }
}
