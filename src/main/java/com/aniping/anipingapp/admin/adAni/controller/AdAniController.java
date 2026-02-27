package com.aniping.anipingapp.admin.adAni.controller;

import com.aniping.anipingapp.admin.adAni.dto.AdAniDto;
import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import com.aniping.anipingapp.admin.adAni.service.AdAniService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminAni")
@RequiredArgsConstructor
public class AdAniController {

    private final AdAniService adAniService;

    //전체조회
    @GetMapping
    public ResponseEntity<List<AdAniDto>> getAllAnis() {
        List<AdAniDto> dtos = adAniService.findAllAnis();
        return ResponseEntity.ok(dtos);
    }

    //상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdAniDto> getAniById(@PathVariable Integer id) {
        return ResponseEntity.ok(adAniService.getAniById(id));
    }

    @PostMapping
    public ResponseEntity<AdAniDto> createAni(@RequestBody AdAniDto dto) {
        AdAniDto savedDto = adAniService.saveAni(dto);

        return ResponseEntity.ok(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAni(@PathVariable Integer id, @RequestBody AdAniDto dto) {
        adAniService.updateAni(id, dto);
        return ResponseEntity.ok().build();
    }
    
    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAni(@PathVariable Integer id) {
        adAniService.deleteAni(id);
        return ResponseEntity.ok().build();
    }


}
