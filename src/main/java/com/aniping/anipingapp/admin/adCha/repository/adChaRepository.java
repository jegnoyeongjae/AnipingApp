package com.aniping.anipingapp.admin.adCha.repository;

import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface adChaRepository extends JpaRepository<adChaEntity, Integer> {
    List<adChaEntity> findByAniId(Integer aniId);

    @Query("SELECT c FROM adChaEntity c " +
           "LEFT JOIN c.user u " +
           "LEFT JOIN c.animation a " +
           "WHERE (:keyword IS NULL OR c.name LIKE %:keyword% OR u.nickname LIKE %:keyword% OR a.title LIKE %:keyword%) " +
           "AND (:status IS NULL OR c.active = :status)")
    Page<adChaEntity> findWithFilters(
            @Param("keyword") String keyword, 
            @Param("status") String status, 
            Pageable pageable
    );
}
