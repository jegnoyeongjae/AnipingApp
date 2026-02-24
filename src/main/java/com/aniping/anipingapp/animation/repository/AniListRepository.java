package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.animation.entity.Anilist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AniListRepository extends JpaRepository<Anilist, Integer> {
    // 카테고리별로 리스트를 가져오고 싶을 때를 대비해 미리 만들어둡니다.
    List<Anilist> findByCateId(Integer cateId);
}