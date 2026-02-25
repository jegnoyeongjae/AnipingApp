package com.aniping.anipingapp.admin.adVoiceActor.controller;

import com.aniping.anipingapp.admin.adVoiceActor.dto.adVADto;
import com.aniping.anipingapp.admin.adVoiceActor.service.adVaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminVA")
@RequiredArgsConstructor
public class adVAController {
    private final adVaService adVaSer;

    // 전체 성우 목록 조회
    @GetMapping
    public ResponseEntity<List<adVADto>> list() {
        return ResponseEntity.ok(adVaSer.findAll());
    }

    // 성우 등록 및 수정 (ID 유무에 따라 service에서 처리)
    @PostMapping("/save")
    public ResponseEntity<adVADto> save(@RequestBody adVADto dto) {
        return ResponseEntity.ok(adVaSer.save(dto));
    }

    // 단일 성우 상세 조회 (수정 폼 채울 때 필요)
    @GetMapping("/{id}")
    public ResponseEntity<adVADto> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(adVaSer.findById(id));
    }

    // 성우 삭제 (Soft Delete 또는 Hard Delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adVaSer.delete(id);
        return ResponseEntity.noContent().build();
    }
}
