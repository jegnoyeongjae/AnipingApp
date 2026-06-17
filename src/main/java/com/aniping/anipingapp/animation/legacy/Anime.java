//package com.aniping.anipingapp.animation.legacy;
//
//
//import com.aniping.anipingapp.animation.entity.Grade;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//
//public class Anime {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    //제목
//    @Column(nullable = false, length = 200)
//    private String title;
//
//    //줄거리
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    //감독
//    @Column(length = 100)
//    private String director;
//
//    //제작사
//    @Column(length = 100)
//    private String studio;
//
//    //카테고리 (판타지, 로맨스 등등)
//    @Column(nullable = false)
//    private String category;
//
//    //방영 시작일
//    private LocalDate date;
//
//    // 시청 연령 등급
//    @Enumerated(EnumType.STRING)
//    private Grade grade;
//
//    //PV영상 URL
//    @Column(columnDefinition = "TEXT")
//    private String aniPv;
//
//    //애니 대표이미지
//    @Column(name = "imgUrl")
//    private String imgUrl;
//
//    //조회수
//    private Long viewCount = 0L;
//
//    //좋아요 수
//    private Long likes = 0L;
//
//    //생성,수정시간
//    private LocalDateTime createAt;
//    private LocalDateTime updateAt;
//
//    //JPA 생명주기 콜백
//
//    @PrePersist
//    protected void onCreate() {
//        this.createAt = LocalDateTime.now();
//        this.updateAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updateAt = LocalDateTime.now();
//    }
//}
