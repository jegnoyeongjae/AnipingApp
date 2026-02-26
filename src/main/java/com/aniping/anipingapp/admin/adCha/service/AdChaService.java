package com.aniping.anipingapp.admin.adCha.service;

import com.aniping.anipingapp.admin.adAni.dto.AdAniDto;
import com.aniping.anipingapp.admin.adAni.repository.AdAniRepository;
import com.aniping.anipingapp.admin.adCha.dto.adChaDto;
import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import com.aniping.anipingapp.admin.adCha.repository.adChaRepository;
import com.aniping.anipingapp.admin.adUser.repository.AdUserRepository;
import com.aniping.anipingapp.admin.adVoiceActor.repository.adVARepository;
import com.aniping.anipingapp.global.constant.TargetType;
import com.aniping.anipingapp.global.file.dto.FileResponseDto;
import com.aniping.anipingapp.global.file.entity.File;
import com.aniping.anipingapp.global.file.repository.FileRepository;
import com.aniping.anipingapp.global.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdChaService {

    private final adChaRepository adChaRepo;
    private final adVARepository adVaRepo;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final AdUserRepository userRepository;
    private final AdAniRepository adAniRepo;

    public List<adChaDto> getCharactersByAniId(Integer aniId) {
        return adChaRepo.findByAniId(aniId).stream()
                .map(entity -> {
                    adChaDto dto = convertToDto(entity);

                    // 성우 이름 가져오기
                    if (entity.getCvId() != null) {
                        adVaRepo.findById(entity.getCvId())
                                .ifPresent(va -> dto.setVoiceActorName(va.getName()));
                    }

                    // 이미지 가져오기
                    List<FileResponseDto> files = fileService.getFilesByTarget(TargetType.CHARACTER, entity.getId());
                    if (!files.isEmpty()) {
                        dto.setImage(files.get(0).getFileUrl());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public adChaDto saveCharacter(adChaDto dto) {
        adChaEntity entity;

        if (dto.getId() != null && dto.getId() > 0) {
            entity = adChaRepo.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 캐릭터가 없습니다. id=" + dto.getId()));
            entity.setName(dto.getName());
            entity.setCvId(dto.getCvId());
            entity.setAniId(dto.getAniId());
        } else {
            entity = adChaEntity.builder()
                    .aniId(dto.getAniId())
                    .cvId(dto.getCvId())
                    .name(dto.getName())
                    .active("accept")
                    .voteCount(0)
                    .build();
        }

        adChaEntity saved = adChaRepo.save(entity);

        // 반환 시 성우 이름까지 포함해서 반환하도록 처리
        adChaDto responseDto = convertToDto(saved);
        if (saved.getCvId() != null) {
            adVaRepo.findById(saved.getCvId())
                    .ifPresent(va -> responseDto.setVoiceActorName(va.getName()));
        }
        return responseDto;
    }

    @Transactional
    public void deleteCharacter(Integer id) {
        adChaEntity character = adChaRepo.findById(id).orElseThrow();

        List<File> files = fileRepository.findByTargetTypeAndTargetIdAndStatus(
                TargetType.CHARACTER, id, File.FileStatus.ACTIVE);

        if (!files.isEmpty()) {
            fileRepository.deleteAll(files);
        }

        adChaRepo.delete(character);
    }

    private adChaDto convertToDto(adChaEntity entity) {
        return adChaDto.builder()
                .id(entity.getId())
                .aniId(entity.getAniId())
                .name(entity.getName())
                .cvId(entity.getCvId())
                .active(entity.getActive())
                .userId(entity.getUserId())
                .createAt(entity.getCreateAt())
                .build();
    }

    //characterBoard
    public List<adChaDto> getAllRequests() {
        return adChaRepo.findAll().stream()
                .map(entity -> {
                    adChaDto dto = convertToDto(entity);
                    if (entity.getUserId() != null) {
                        userRepository.findById(entity.getUserId())
                                .ifPresent(user -> dto.setNickname(user.getNickname()));
                    }
                    if (entity.getAniId() != null) {
                        adAniRepo.findById(entity.getAniId())
                                .map(AdAniDto::fromEntity)
                                .ifPresent(aniDto -> dto.setAnimeTitle(aniDto.getTitle()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveCharacter(Integer id) {
        adChaEntity entity = adChaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("캐릭터를 찾을 수 없습니다."));
        entity.setActive("accept");
    }
}
