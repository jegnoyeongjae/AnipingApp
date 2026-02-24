//package com.aniping.anipingapp.animation.legacy;
//
//import com.aniping.anipingapp.animation.entity.Anilist;
//import jakarta.persistence.*;
//
//
//public class AnimeCategory {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "anime_id", nullable = false)
//    private Anilist aniList;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//}
