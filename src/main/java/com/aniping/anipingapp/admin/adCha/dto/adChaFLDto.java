package com.aniping.anipingapp.admin.adCha.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adChaFLDto {
    private Integer id;
    private String content;
    private String active;
    private LocalDateTime createAt;

    private Integer charId;
    private String charName;
    private String charImage;
    private Integer userId;
    private String userNickname;

}
