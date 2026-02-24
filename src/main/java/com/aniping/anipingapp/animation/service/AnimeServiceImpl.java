package com.aniping.anipingapp.animation.service;

import com.aniping.anipingapp.animation.dto.AnilistResponseDto;
import com.aniping.anipingapp.animation.entity.*;
import com.aniping.anipingapp.animation.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    private final AniListRepository aniListRepository;
    private final AnimeFileRepository animefileRepository; // 파일 리포지토리 추가!

    @Override
    public List<AnilistResponseDto> getAnimeList(String category, String order, Integer limit) {
        List<Anilist> animes;

        // 카테고리(cateId)가 들어왔을 때만 필터링!
        if (category != null && !category.isBlank()) {
            // category가 숫자로 들어온다고 가정 (cateId 필터링)
            Integer cateId = Integer.parseInt(category);
            animes = aniListRepository.findByCateId(cateId);
        } else {
            animes = aniListRepository.findAll();
        }

        Stream<AnilistResponseDto> stream = animes.stream().map(this::convertToDto);

        // 정렬 및 제한 로직 생략 (기존과 동일)
        return stream.toList();
    }

    @Override
    public AnilistResponseDto getAnime(Long id) {
        Anilist anime = aniListRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("해당 애니는 없습니다. ID: " + id));

        return convertToDto(anime);
    }

    @Override
    public void increaseView(Long id) {
        Anilist anime = aniListRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("해당 애니는 없습니다."));

        // String -> Integer 변환 후 증가 로직 (필요시)
        int currentView = (anime.getViewCount() == null) ? 0 : Integer.parseInt(anime.getViewCount());
        anime.setViewCount(String.valueOf(currentView + 1));

        aniListRepository.save(anime);
    }

    // 헬퍼 메소드: 엔티티를 DTO로 바꾸면서 이미지(S3Key)를 찾아옴
    private AnilistResponseDto convertToDto(Anilist anime) {
        String s3Key = animefileRepository.findByTargetIdAndTargetTypeAndStatus(
                anime.getId(),
                TargetType.ANILIST,
                Status.ACTIVE
        ).map(FileEntity::getS3Key).orElse(null);

        return new AnilistResponseDto(anime, s3Key);
    }
}