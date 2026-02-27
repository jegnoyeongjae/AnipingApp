package com.aniping.anipingapp.admin.adCha.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adChaDto {
    private Integer id;
    private Integer aniId;
    private String name;
    private Integer cvId;
    private String voiceActorName;
    private String image;
    private String active;
    private String animeTitle;
    private Integer userId;
    private LocalDateTime createAt;
    private String nickname;

    // JPQL에서 사용할 생성자
    public adChaDto(Integer id, String name, String active, LocalDateTime createAt, String nickname, String animeTitle) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createAt = createAt;
        this.nickname = nickname;
        this.animeTitle = animeTitle;
    }
}
