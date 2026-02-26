package com.aniping.anipingapp.admin.adCha.controller;

import com.aniping.anipingapp.admin.adCha.dto.adChaFLDto;
import com.aniping.anipingapp.admin.adCha.service.AdChaFLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminChaFL")
@RequiredArgsConstructor
public class AdChaFLController {

    private final AdChaFLService adChaFLService;

    @GetMapping
    public ResponseEntity<List<adChaFLDto>> getAllFamousLines() {
        List<adChaFLDto> list = adChaFLService.findAllWithDetails();
        return ResponseEntity.ok(list);
    }


    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Integer id) {
        adChaFLService.approveRequest(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 3. 명대사 신청 거절 (waiting -> reject)
     */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Integer id) {
        adChaFLService.rejectRequest(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 4. 명대사 데이터 삭제 (Soft Delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adChaFLService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}