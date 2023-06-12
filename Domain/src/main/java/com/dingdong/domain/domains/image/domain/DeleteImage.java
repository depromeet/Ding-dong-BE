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
@Table(name = "tbl_delete_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteImage extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String imageUrl;

    private DeleteImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static DeleteImage toEntity(String imageUrl) {
        return new DeleteImage(imageUrl);
    }
}
