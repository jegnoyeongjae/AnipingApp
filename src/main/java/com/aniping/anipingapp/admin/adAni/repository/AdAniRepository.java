package com.aniping.anipingapp.admin.adAni.repository;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AdAniRepository extends JpaRepository<AdAniEntity, Integer> {
    List<AdAniEntity> findAllByDeleteAtIsNull();
    Optional<AdAniEntity> findByIdAndDeleteAtIsNull(Integer id);
    boolean existsByTitle(String title);
}
