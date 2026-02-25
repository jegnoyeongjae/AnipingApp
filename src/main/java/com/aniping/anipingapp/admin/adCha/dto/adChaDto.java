package com.aniping.anipingapp.admin.adCha.dto;

import lombok.*;

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
    private String image;
}
