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
    private String categoryName;

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
                .viewCount(entity.getViewCount())
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
                .build();
    }
}
