package com.aniping.anipingapp.animation.controller;


import com.aniping.anipingapp.animation.dto.AnilistResponseDto;
import com.aniping.anipingapp.animation.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    //애니 목록 조회
    @GetMapping
    public List<AnilistResponseDto> getAnimeList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer limit
    ) {
        return animeService.getAnimeList(category, order, limit);
    }

    //애니 상세 조회
    @GetMapping("/{id}")
    public AnilistResponseDto getAnimeDetail(@PathVariable Long id) {
        return animeService.getAnime(id);
    }

//    //애니 등록 (나중에 따로 등록해야 할 경우 주석해제)
//    @PostMapping
//    public Anime createAnime(@RequestBody Anime anime) {
//        return animeService.saveAnime(anime);
//    }

    //조회수 증가
    @PostMapping("/{id}/view")
    public void increaseView(@PathVariable Long id) {
        animeService.increaseView(id);
    }
}

