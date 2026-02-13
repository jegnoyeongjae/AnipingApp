package com.aniping.anipingapp.animation.service;

import com.aniping.anipingapp.animation.entity.AniList;
import com.aniping.anipingapp.animation.entity.Anime;
import com.aniping.anipingapp.animation.entity.AnimeCategory;
import com.aniping.anipingapp.animation.entity.Category;
import com.aniping.anipingapp.animation.repository.AniListRepository;
import com.aniping.anipingapp.animation.repository.AnimeCategoryRepository;
import com.aniping.anipingapp.animation.repository.AnimeRepository;
import com.aniping.anipingapp.animation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    private final AniListRepository aniListRepository;
    private final CategoryRepository categoryRepository;
    private final AnimeCategoryRepository animeCategoryRepository;
    private final AnimeRepository animeRepository;

    @Override
    public List<AniList> getAnimeList(String category, String order, Integer limit) {
        if(category == null || category.isBlank()) {
            List<AniList> all = aniListRepository.findAll();
            return limit != null ? all.stream().limit(limit).toList() : all;
        }

        Category categoryEntity = categoryRepository.findByName(category);
        List<AnimeCategory> animeCategories = animeCategoryRepository.findByCategory(categoryEntity);

        Stream<AniList> stream = animeCategories.stream().map(AnimeCategory::getAniList);

        //order 정렬
        if (order != null && order.isBlank()) {
            stream = stream.sorted(Comparator.comparing(AniList::getLikes).reversed());
        }

        if (limit != null) {
            stream = stream.limit(limit);
        }

        return stream.toList();
//        if (order == null || order.isBlank()) return animeCategories.stream().map(AnimeCategory::getAniList).toList();
//        return animeCategories.stream().map(AnimeCategory::getAniList).sorted(Comparator.comparingLong(AniList::getLikes)).toList().reversed();
    }

    @Override
    public Anime getAnime(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 애니는 없습니다."));
    }

    @Override
    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public void increaseView(Long id) {
        Anime anime = getAnime(id);
        anime.setViewCount(anime.getViewCount() + 1);
        animeRepository.save(anime);
    }

}