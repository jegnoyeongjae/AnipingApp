package com.aniping.anipingapp.admin.adCha.repository;


import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface adChaRepository extends JpaRepository<adChaEntity, Integer> {
    List<adChaEntity> findByAniId(Integer aniId);
}
