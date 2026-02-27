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
}
