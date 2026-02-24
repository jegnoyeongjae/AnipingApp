package com.aniping.anipingapp.animation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anilist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder // 데이터 생성 시 편리함을 위해 추가
public class Anilist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String title;

    private String director;
    private String studio;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    // DB의 ENUM 타입과 매칭 (문자열로 저장/조회)
    private String grade;

    @Column(name = "aniPv", columnDefinition = "TEXT")
    private String aniPv;

    //mediumtext 설정에 맞춤
    @Column(columnDefinition = "MEDIUMTEXT")
    private String viewCount;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String likes;


    private Integer cateId;

    @Column(name = "createAt", updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "updateAt", insertable = false, columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt;

    @Column(name = "deleteAt", insertable = false, columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime deleteAt;
}