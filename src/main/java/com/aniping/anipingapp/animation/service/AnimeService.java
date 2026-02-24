package com.aniping.anipingapp.animation.service;

import com.aniping.anipingapp.animation.dto.AnilistResponseDto;
import java.util.List;

public interface AnimeService {
    // 리턴 타입을 DTO 리스트로 변경
    List<AnilistResponseDto> getAnimeList(String category, String order, Integer limit);

    // 상세 조회도 DTO로 변경
    AnilistResponseDto getAnime(Long id);

    // 조회수 증가는 그대로 유지 (리턴값 없음)
    void increaseView(Long id);

    // 등록 기능은 현재 DB 구조에 맞춰 추후 보완 (우선 목록/상세에 집중!)
}