package com.aniping.anipingapp.admin.adCha.repository;

import com.aniping.anipingapp.admin.adCha.entity.AdChaFLEntity;
import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdChaFLRepository extends JpaRepository<AdChaFLEntity, Integer> {
    List<AdChaFLEntity> findByDeleteAtIsNullOrderByCreateAtDesc();

    List<AdChaFLEntity> findByActiveAndDeleteAtIsNullOrderByCreateAtDesc(String active);

    List<AdChaFLEntity> findByContentContainingAndDeleteAtIsNull(String content);

    List<AdChaFLEntity> findByCharIdAndDeleteAtIsNull(Integer charId);
}