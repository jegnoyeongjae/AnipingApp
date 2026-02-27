package com.aniping.anipingapp.admin.adCha.service;

import com.aniping.anipingapp.admin.adCha.dto.adChaFLDto;
import com.aniping.anipingapp.admin.adCha.entity.AdChaFLEntity;
import com.aniping.anipingapp.admin.adCha.entity.adChaEntity;
import com.aniping.anipingapp.admin.adCha.repository.AdChaFLRepository;
import com.aniping.anipingapp.admin.adCha.repository.adChaRepository;
import com.aniping.anipingapp.admin.adUser.repository.AdUserRepository;
import com.aniping.anipingapp.admin.entity.Report;
import com.aniping.anipingapp.global.constant.TargetType;
import com.aniping.anipingapp.global.file.dto.FileResponseDto;
import com.aniping.anipingapp.global.file.entity.File;
import com.aniping.anipingapp.global.file.repository.FileRepository;
import com.aniping.anipingapp.user.entity.UserEntity;
import com.aniping.anipingapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdChaFLService {
    private final AdChaFLRepository adChaFLRepository;
    private final adChaRepository adChaRepository; // 캐릭터 레포지토리 가정
    private final UserRepository userRepository;   // 유저 레포지토리 가정
    private final FileRepository fileRepository;   // 파일 레포지토리 가정

        @Transactional(readOnly = true)
        public List<adChaFLDto> findAllWithDetails() {
            List<AdChaFLEntity> entities = adChaFLRepository.findByDeleteAtIsNullOrderByCreateAtDesc();

            return entities.stream().map(entity -> {
                Integer targetCharId = entity.getCharId();
                String charName = "관리자 입력(캐릭터 미지정)";
                String charImage = null;

                if (targetCharId != null) {
                    charName = adChaRepository.findById(targetCharId)
                            .map(c -> c.getName())
                            .orElse("알 수 없는 캐릭터(ID: " + targetCharId + ")");

                    // 이미지 조회도 targetCharId가 있을 때만 수행
                    charImage = fileRepository.findFirstByTargetTypeAndTargetIdAndStatus(
                                    TargetType.CHARACTER,
                                    targetCharId,
                                    File.FileStatus.ACTIVE
                            )
                            .map(file -> {return file.getS3Key();})
                            .orElse(null);
                }

                // 2. 유저 정보 처리 (userId가 null인 경우 대비)
                String userNickname = "관리자 입력";
                if (entity.getUserId() != null) {
                    userNickname = userRepository.findById(Long.valueOf(entity.getUserId()))
                            .map(u -> u.getNickname())
                            .orElse("탈퇴한 회원(ID: " + entity.getUserId() + ")");
                }

                // 3. DTO 빌드
                return adChaFLDto.builder()
                        .id(entity.getId())
                        .content(entity.getContent())
                        .active(entity.getActive())
                        .createAt(entity.getCreateAt())
                        .charId(targetCharId)
                        .charName(charName)
                        .charImage(charImage)
                        .userId(entity.getUserId())
                        .userNickname(userNickname)
                        .build();
            }).toList();
        }

        private AdChaFLEntity findByIdOrThrow(Integer id) {
            return adChaFLRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID " + id + "에 해당하는 데이터를 찾을 수 없습니다."));
        }

        @Transactional
        public void approveRequest(Integer id) {
            findByIdOrThrow(id).setActive("accept");
        }


        @Transactional
        public void rejectRequest(Integer id) {
            AdChaFLEntity entity = adChaFLRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 명대사 신청 건을 찾을 수 없습니다. ID: " + id));

            entity.setActive("reject");
        }

        @Transactional
        public void softDelete(Integer id) {
            AdChaFLEntity entity = adChaFLRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 명대사 신청 건을 찾을 수 없습니다. ID: " + id));

            entity.setDeleteAt(LocalDateTime.now());
        }
}
