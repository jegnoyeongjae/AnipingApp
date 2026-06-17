package com.aniping.anipingapp.animation.dto;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import com.aniping.anipingapp.admin.adTags.entity.AdTagsEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AniListCategoryDto {
    private Integer id;
    private String title;
    private String director;
    private String studio;
    private String description;
    private LocalDate date;
    private String grade;
    private String imgUrl;
    private AdTagsEntity category;

    public AniListCategoryDto(AdAniEntity anilist,AdTagsEntity category, String s3Key) {
        this.id = anilist.getId();
        this.title = anilist.getTitle();
        this.director = anilist.getDirector();
        this.studio = anilist.getStudio();
        this.description = anilist.getDescription();
        this.date = anilist.getDate();
        this.grade = anilist.getGrade();
        this.category = category;
        this.imgUrl = s3Key;
    }

}
