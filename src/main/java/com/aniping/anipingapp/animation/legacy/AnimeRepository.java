package com.aniping.anipingapp.animation.legacy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

//카테고리별 애니 조회
    List<Anime> findByCategory(String category);
}
