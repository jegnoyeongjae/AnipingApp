package com.aniping.anipingapp.admin.adAni.dto;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdAniDto {
    private Integer id;
    private String title;
    private String director;
    private String studio;
    private String description;
    private LocalDate date;
    private String grade;
    private String aniPv;
    private Integer cateId;
    private Integer viewCount;
    private Integer likes;
    private String categoryName;
    private String imageUrl; // 이미지 URL 필드 추가

    public static AdAniDto fromEntity(AdAniEntity entity) {
        return AdAniDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .director(entity.getDirector())
                .studio(entity.getStudio())
                .description(entity.getDescription())
                .date(entity.getDate())
                .grade(entity.getGrade())
                .aniPv(entity.getAniPv())
                .cateId(entity.getCateId())
                .viewCount(entity.getViewCount() != null ? entity.getViewCount() : 0)
                .likes(entity.getLikes() != null ? entity.getLikes() : 0)
                .build();
    }

    public AdAniEntity toEntity() {
        return AdAniEntity.builder()
                .id(this.id)
                .title(this.title)
                .director(this.director)
                .studio(this.studio)
                .description(this.description)
                .date(this.date)
                .grade(this.grade)
                .aniPv(this.aniPv)
                .cateId(this.cateId)
                .viewCount(this.viewCount != null ? this.viewCount : 0)
                .likes(this.likes != null ? this.likes : 0)
                .build();
    }
}
