package com.aniping.anipingapp.animation.service;

import com.aniping.anipingapp.animation.entity.AniList;
import com.aniping.anipingapp.animation.entity.Anime;


import java.util.List;

public interface AnimeService {

    //애니목록 조회(카테고리 필터 기능)
    List<AniList> getAnimeList(String category);

    //애니 상세 조회
    Anime getAnime(Long id);

    //애니 등록
    Anime saveAnime(Anime anime);

    void increaseView(Long id);

}
