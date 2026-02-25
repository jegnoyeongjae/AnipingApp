package com.aniping.anipingapp.admin.adAni.service;

import com.aniping.anipingapp.admin.adAni.dto.AdAniDto;
import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import com.aniping.anipingapp.admin.adAni.repository.AdAniRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdAniService {

    private final AdAniRepository adAniRepository;

    public List<AdAniDto> findAllAnis() {
        List<AdAniEntity> entities = adAniRepository.findAllByDeleteAtIsNull();

        return entities.stream()
                .map(entity -> AdAniDto.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .director(entity.getDirector())
                        .studio(entity.getStudio())
                        .cateId(entity.getCateId())
                        .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : "미지정")
                        .viewCount(entity.getViewCount())
                        .build())
                .toList();
    }

    public AdAniDto getAniById(Integer id) {
        AdAniEntity entity = adAniRepository.findByIdAndDeleteAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니메이션이 없습니다."));

        return AdAniDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .director(entity.getDirector())
                .studio(entity.getStudio())
                .description(entity.getDescription())
                .date(entity.getDate())
                .grade(entity.getGrade())
                .aniPv(entity.getAniPv())
                .cateId(entity.getCateId())
                .build();
    }

    @Transactional
    public void saveAni(AdAniDto dto) {
        adAniRepository.save(dto.toEntity());
    }

    @Transactional
    public void updateAni(Integer id, AdAniDto dto) {
        AdAniEntity ani = adAniRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니메이션이 없습니다."));

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
