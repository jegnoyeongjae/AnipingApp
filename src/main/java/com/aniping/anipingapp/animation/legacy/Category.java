//package com.aniping.anipingapp.animation.legacy;
//
//import jakarta.persistence.*;
//
//
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(nullable = false, unique = true, length = 50)
//    private String name;
//
//    @Column(unique = true, length = 50)
//    private String slug;
//
//    @Column(unique = true)
//    private Integer sequence;
//}