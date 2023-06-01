package com.dingdong.domain.domains.community.domain;


import com.dingdong.domain.domains.community.domain.enums.AdminRole;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_admin")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @NotNull private Long userId;

    private Admin(AdminRole role, Long userId) {
        this.role = role;
        this.userId = userId;
    }

    public static Admin toEntity(AdminRole role, Long userId) {
        return new Admin(role, userId);
    }
}
