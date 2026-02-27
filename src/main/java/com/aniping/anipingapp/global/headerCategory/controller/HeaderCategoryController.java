package com.aniping.anipingapp.global.headerCategory.controller;

import com.aniping.anipingapp.admin.adTags.dto.AdTagsDto;
import com.aniping.anipingapp.admin.adTags.service.AdTagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/header/categories")
@RequiredArgsConstructor
public class HeaderCategoryController {

    private final AdTagsService adTagsService;

    @GetMapping
    public ResponseEntity<List<AdTagsDto>> getCategories() {
        return ResponseEntity.ok(adTagsService.findAllTags());
    }
}
