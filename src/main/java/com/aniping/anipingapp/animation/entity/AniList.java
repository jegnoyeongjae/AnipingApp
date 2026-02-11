package com.aniping.anipingapp.animation.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anilist")
@Getter
@Setter
public class AniList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //제목
    @Column(nullable = false, length = 200)
    private String title;

    //감독
    @Column(length = 100)
    private String director;

    //줄거리
    @Column(columnDefinition = "TEXT")
    private String description;

    //제작사
    @Column(length = 100)
    private String studio;

    //방영 시작일
    @Column
    private LocalDate airdate;

    //방영날짜
    @Column
    private  LocalDate date;

    // 시청 연령 등급
    @Enumerated(EnumType.STRING)
    private Grade grade;

    //PV영상 URL
    @Column(columnDefinition = "TEXT")
    private String aniPv;

    //애니 대표이미지
    @Column(name = "imgUrl")
    private String imgUrl;

    //조회수
    @Column
    private Long viewCount = 0L;


    //좋아요 수
    @Column
    private Long likes = 0L;

    //생성,수정시간
    @Column
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    //JPA 생명주기 콜백

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}

