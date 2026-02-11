package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.animation.entity.AnimeCategory;
import com.aniping.anipingapp.animation.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeCategoryRepository extends JpaRepository<AnimeCategory, Long> {

//카테고리별 애니 조회
    List<AnimeCategory> findByCategory(Category category);
}
