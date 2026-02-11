package com.aniping.anipingapp.animation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anime_category")
@Getter
@Setter
public class AnimeCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private AniList aniList;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
