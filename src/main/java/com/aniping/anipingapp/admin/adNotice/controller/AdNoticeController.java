package com.aniping.anipingapp.admin.adNotice.controller;

import com.aniping.anipingapp.admin.adNotice.dto.AdNoticeDto;
import com.aniping.anipingapp.admin.adNotice.service.AdNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminNotice")
@RequiredArgsConstructor
public class AdNoticeController {

    private final AdNoticeService adNoticeService;

    @GetMapping("/")
    public ResponseEntity<List<AdNoticeDto>> getAllNotices() {
        List<AdNoticeDto> notices = adNoticeService.findAllNotices();
        return ResponseEntity.ok(notices);
    }

    @PostMapping("/")
    public ResponseEntity<AdNoticeDto> createNotice(@RequestBody AdNoticeDto adNoticeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        AdNoticeDto createdNotice = adNoticeService.createNotice(adNoticeDto, loginId);
        return new ResponseEntity<>(createdNotice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdNoticeDto> updateNotice(@PathVariable Integer id, @RequestBody AdNoticeDto adNoticeDto) {
        AdNoticeDto updatedNotice = adNoticeService.updateNotice(id, adNoticeDto);
        return ResponseEntity.ok(updatedNotice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Integer id) {
        adNoticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
