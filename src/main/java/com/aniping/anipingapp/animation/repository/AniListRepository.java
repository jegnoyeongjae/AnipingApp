package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AniListRepository extends JpaRepository<AdAniEntity, Integer> {
    // 네이티브 쿼리(Native Query)를 사용하면 DB 컬럼명 그대로 사용할 수 있어 가장 안전합니다.
    @Query(value = "SELECT a.* FROM anilist a " +
            "JOIN category c ON a.cateId = c.id " + // category 테이블은 id 컬럼 사용
            "WHERE c.slug = :slug",
            nativeQuery = true)
    List<AdAniEntity> findBySlugDirectly(@Param("slug") String slug);

    // Native Query를 사용해 DB 구조(id 컬럼)에 맞게 직접 매핑
    @Query(value = "SELECT a.* FROM anilist a " +
            "JOIN category c ON a.cateId = c.id " +
            "WHERE c.slug = :slug",
            nativeQuery = true)
    List<AdAniEntity> findBySlug(@Param("slug") String slug);
}
