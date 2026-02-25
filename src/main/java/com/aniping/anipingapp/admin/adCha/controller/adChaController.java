package com.aniping.anipingapp.admin.adCha.controller;

import com.aniping.anipingapp.admin.adCha.dto.adChaDto;
import com.aniping.anipingapp.admin.adCha.service.AdChaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminChaBoard")
@RequiredArgsConstructor
public class adChaController {
    private final AdChaService adChaService;

    @PostMapping
    public ResponseEntity<adChaDto> createCharacter(@RequestBody adChaDto dto) {
        adChaDto savedDto = adChaService.saveCharacter(dto);
        return ResponseEntity.ok(savedDto);
    }

    // 특정 애니메이션 ID로 캐릭터 리스트 조회
    @GetMapping("/ani/{aniId}")
    public ResponseEntity<List<adChaDto>> getCharactersByAni(@PathVariable Integer aniId) {
        List<adChaDto> list = adChaService.getCharactersByAniId(aniId);
        return ResponseEntity.ok(list);
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer id) {
        adChaService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
