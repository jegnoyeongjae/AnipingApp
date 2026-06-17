package com.aniping.anipingapp.admin.adVoiceActor.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adVADto {
    private Integer id;
    private String name;
    private String image;
    private String birth;
    private Integer height;
    private BloodType bloodType;
    private String bloodTypeDisplayName; // 엔티티의 getBloodTypeDisplayName() 대응
    private String agency;
    private Integer rank;
    private Integer likes;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public enum BloodType {
        A, B, AB, O, N
    }
}