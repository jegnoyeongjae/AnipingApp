package com.aniping.anipingapp.admin.adVoiceActor.repository;

import com.aniping.anipingapp.admin.adVoiceActor.entity.adVAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface adVARepository extends JpaRepository<adVAEntity, Integer> {
    Optional<adVAEntity> findByName(String name);
}
