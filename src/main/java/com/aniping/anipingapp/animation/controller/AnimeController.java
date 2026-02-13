package com.aniping.anipingapp.animation.controller;


import com.aniping.anipingapp.animation.entity.AniList;
import com.aniping.anipingapp.animation.entity.Anime;
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
    public List<AniList> getAnimeList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer limit
    ) {
        return animeService.getAnimeList(category, order);
    }

    //애니 상세 조회
    @GetMapping("/{id}")
    public Anime getAnimeDetail(@PathVariable Long id) {
        return animeService.getAnime(id);
    }

    //애니 등록
    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.saveAnime(anime);
    }

    //조회수 증가
    @PostMapping("/{id}/view")
    public void increaseView(@PathVariable Long id) {
        animeService.increaseView(id);
    }
}

//    @PostMapping("/anipang")
//    public ResponseEntity<UserRequest> test(@RequestBody UserRequest  userRequest) {
//
//
//        System.out.println("리턴데이터: " + userRequest.toString());
//
//        return ResponseEntity.ok(userRequest);
//    }
//
//    public static class UserRequest {
//        private String test;
//        private String userName;
//
//        // 필드명과 Getter/Setter 이름을 맞춰주세요.
//        public String getTest() { return test; }
//        public void setTest(String test) { this.test = test; }
//        public String getUserName() { return userName; }
//        public void setUserName(String userName) { this.userName = userName; }
//        @Override
//        public String toString() {
//            return "UserRequest{" + "test='" + test + '\'' + ", userName='" + userName + '\'' + '}';
//        }
//    }

