package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.animation.entity.Anime;
import com.aniping.anipingapp.animation.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}