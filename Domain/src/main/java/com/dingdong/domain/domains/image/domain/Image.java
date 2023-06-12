package com.dingdong.domain.domains.image.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String imageUrl;

    private Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Image toEntity(String imageUrl) {
        return new Image(imageUrl);
    }
}
