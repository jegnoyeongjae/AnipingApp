package com.aniping.anipingapp.admin.adNotice.service;

import com.aniping.anipingapp.admin.adNotice.dto.AdNoticeDto;
import com.aniping.anipingapp.admin.adNotice.entity.AdNoticeEntity;
import com.aniping.anipingapp.admin.adNotice.repository.AdNoticeRepository;
import com.aniping.anipingapp.user.entity.UserEntity;
import com.aniping.anipingapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdNoticeService {

    private final AdNoticeRepository adNoticeRepository;
    private final UserRepository userRepository;

    public List<AdNoticeDto> findAllNotices() {
        return adNoticeRepository.findAllByBoardTypeAndDeleteAtIsNullOrderByIdDesc(AdNoticeEntity.BoardType.NOTIFICATION)
                .stream()
                .map(AdNoticeDto::fromEntity) // ::new 대신 fromEntity 사용
                .collect(Collectors.toList());
    }

    @Transactional
    public AdNoticeDto createNotice(AdNoticeDto adNoticeDto, String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        AdNoticeEntity adNoticeEntity = AdNoticeEntity.builder()
                .title(adNoticeDto.getTitle())
                .content(adNoticeDto.getContent())
                .user(user)
                .boardType(AdNoticeEntity.BoardType.NOTIFICATION)
                .build();

        AdNoticeEntity savedEntity = adNoticeRepository.save(adNoticeEntity);
        return AdNoticeDto.fromEntity(savedEntity); // ::new 대신 fromEntity 사용
    }

    @Transactional
    public AdNoticeDto updateNotice(Integer id, AdNoticeDto adNoticeDto) {
        AdNoticeEntity adNoticeEntity = adNoticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다."));

        adNoticeEntity.updateNotice(adNoticeDto.getTitle(), adNoticeDto.getContent());
        return AdNoticeDto.fromEntity(adNoticeEntity); // ::new 대신 fromEntity 사용
    }

    @Transactional
    public void deleteNotice(Integer id) {
        AdNoticeEntity adNoticeEntity = adNoticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다."));
        adNoticeEntity.deletedTime();
    }
}
