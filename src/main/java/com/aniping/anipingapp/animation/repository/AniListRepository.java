package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AniListRepository extends JpaRepository<AdAniEntity, Integer> {
    // 카테고리별로 리스트를 가져오고 싶을 때를 대비해 미리 만들어둡니다.
    @Query("SELECT a FROM AdAniEntity a WHERE category = :category")
    List<AdAniEntity> findByCategory(@Param("category") String category);
}