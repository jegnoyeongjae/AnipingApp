package com.aniping.anipingapp.admin.adCha.service;

import com.aniping.anipingapp.admin.adCha.dto.adChaDto;
import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import com.aniping.anipingapp.admin.adCha.repository.adChaRepository;
import com.aniping.anipingapp.global.constant.TargetType;
import com.aniping.anipingapp.global.file.dto.FileResponseDto;
import com.aniping.anipingapp.global.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdChaService {

    private final adChaRepository adChaRepo;
    private final FileService fileService;

    // 애니메이션의 캐릭터 조회
    public List<adChaDto> getCharactersByAniId(Integer aniId) {
        return adChaRepo.findByAniId(aniId).stream()
                .map(entity -> {
                    adChaDto dto = convertToDto(entity);

                    List<FileResponseDto> files = fileService.getFilesByTarget(TargetType.CHARACTER, entity.getId());

                    if (!files.isEmpty()) {
                        dto.setImage(files.get(0).getFileUrl());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 캐릭터 저장 (생성 및 수정 공용)
    @Transactional
    public adChaDto saveCharacter(adChaDto dto) {
        adChaEntity entity;

        if (dto.getId() != null && dto.getId() > 0) {
            // 수정 모드
            entity = adChaRepo.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 캐릭터가 없습니다. id=" + dto.getId()));
            entity.setName(dto.getName());
            entity.setCvId(dto.getCvId());
            // 필요한 필드 업데이트...
        } else {
            // 신규 등록 모드
            entity = adChaEntity.builder()
                    .aniId(dto.getAniId())
                    .cvId(dto.getCvId())
                    .name(dto.getName())
                    .active("accept") // 관리자 등록이므로 즉시 승인 상태
                    .voteCount(0)
                    .build();
        }

        adChaEntity saved = adChaRepo.save(entity);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteCharacter(Integer id) {
        adChaRepo.deleteById(id);
    }

    private adChaDto convertToDto(adChaEntity entity) {
        return adChaDto.builder()
                .id(entity.getId())
                .aniId(entity.getAniId())
                .name(entity.getName())
                .cvId(entity.getCvId())
                .build();
    }
}
