package com.aniping.anipingapp.animation.dto; // 이 경로가 맞는지 확인!

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor // JSON 변환을 위해 기본 생성자가 필요할 수 있습니다
public class AnilistResponseDto {
    private Integer id;
    private String title;
    private String director;
    private String studio;
    private String description;
    private LocalDate date;
    private String grade;
    private String imageUrl; // 필드명 변경

    public AnilistResponseDto(AdAniEntity anilist, String s3Key) {
        this.id = anilist.getId();
        this.title = anilist.getTitle();
        this.director = anilist.getDirector();
        this.studio = anilist.getStudio();
        this.description = anilist.getDescription();
        this.date = anilist.getDate();
        this.grade = anilist.getGrade();
        this.imageUrl = s3Key; // 필드명 변경
    }
}
