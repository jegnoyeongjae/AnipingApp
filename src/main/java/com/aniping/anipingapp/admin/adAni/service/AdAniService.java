package com.aniping.anipingapp.admin.adAni.service;

import com.aniping.anipingapp.admin.adAni.dto.AdAniDto;
import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import com.aniping.anipingapp.admin.adAni.repository.AdAniRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdAniService {

    private final AdAniRepository adAniRepository;

    public Page<AdAniDto> findAllAnis(Pageable pageable) {
        Page<AdAniEntity> entities = adAniRepository.findAll(pageable);

        return entities.map(entity -> {
            AdAniDto dto = AdAniDto.fromEntity(entity);

            if (dto.getCategoryName() == null) {
                dto.setCategoryName("미지정");
            }
            return dto;
        });
    }

    public AdAniDto getAniById(Integer id) {
        AdAniEntity entity = adAniRepository.findByIdAndDeleteAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니메이션이 없습니다."));

        return AdAniDto.fromEntity(entity);
    }

    @Transactional
    public AdAniDto saveAni(AdAniDto dto) {
        // 중복 체크 (신규 등록일 때만)
        if (dto.getId() == null && adAniRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 애니메이션 제목입니다: " + dto.getTitle());
        }

        AdAniEntity entity = dto.toEntity();

        if (entity.getId() == null) {
            if (entity.getViewCount() == null) entity.setViewCount(0);
            if (entity.getLikes() == null) entity.setLikes(0);
        }

        AdAniEntity savedEntity = adAniRepository.save(entity);

        return AdAniDto.fromEntity(savedEntity);
    }

    @Transactional
    public void updateAni(Integer id, AdAniDto dto) {
        AdAniEntity ani = adAniRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니메이션이 없습니다."));
        
        // 제목 변경 시 중복 체크 (자신의 제목은 제외)
        if (!ani.getTitle().equals(dto.getTitle()) && adAniRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 애니메이션 제목입니다: " + dto.getTitle());
        }

        ani.setTitle(dto.getTitle());
        ani.setDirector(dto.getDirector());
        ani.setStudio(dto.getStudio());
        ani.setDescription(dto.getDescription());
        ani.setDate(dto.getDate());
        ani.setGrade(dto.getGrade());
        ani.setAniPv(dto.getAniPv());
        ani.setCateId(dto.getCateId());
    }

    @Transactional
    public void deleteAni(Integer id) {
        AdAniEntity ani = adAniRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니메이션이 없습니다."));
        ani.setDeleteAt(LocalDateTime.now());
    }
}
