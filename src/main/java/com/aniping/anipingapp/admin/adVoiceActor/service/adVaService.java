package com.aniping.anipingapp.admin.adVoiceActor.service;

import com.aniping.anipingapp.admin.adVoiceActor.dto.adVADto;
import com.aniping.anipingapp.admin.adVoiceActor.entity.adVAEntity;
import com.aniping.anipingapp.admin.adVoiceActor.repository.adVARepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class adVaService {

    private final adVARepository adVARep;

    @Transactional(readOnly = true)
    public List<adVADto> findAll() {
        return adVARep.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public adVADto save(adVADto dto) {
        // 수정 시 기존 데이터를 가져오고, 없으면 새 객체 생성
        adVAEntity entity = (dto.getId() != null)
                ? adVARep.findById(dto.getId()).orElse(new adVAEntity())
                : new adVAEntity();

        entity.setName(dto.getName());
        entity.setBirth(dto.getBirth());
        entity.setHeight(dto.getHeight());
        entity.setAgency(dto.getAgency());
        entity.setRank(dto.getRank());

        if (dto.getBloodType() != null) {
            entity.setBloodType(adVAEntity.BloodType.valueOf(dto.getBloodType().name()));
        }

        if (dto.getLikes() != null) {
            entity.setLikes(dto.getLikes());
        }

        adVAEntity saved = adVARep.save(entity);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public adVADto findById(Integer id) {
        adVAEntity entity = adVARep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 성우 정보를 찾을 수 없습니다. id=" + id));
        return convertToDto(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!adVARep.existsById(id)) {
            throw new IllegalArgumentException("삭제하려는 성우가 존재하지 않습니다. id=" + id);
        }
        adVARep.deleteById(id);
    }

    private adVADto convertToDto(adVAEntity entity) {
        // Entity Enum -> DTO Enum 변환
        adVADto.BloodType dtoBloodType = null;
        String displayName = "Unknown";

        if (entity.getBloodType() != null) {
            dtoBloodType = adVADto.BloodType.valueOf(entity.getBloodType().name());
            displayName = entity.getBloodType().name(); // 또는 별도의 로직
        }

        return adVADto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birth(entity.getBirth())
                .height(entity.getHeight())
                .bloodType(dtoBloodType)
                .bloodTypeDisplayName(displayName) // 직접 처리
                .agency(entity.getAgency())
                .rank(entity.getRank())
                .likes(entity.getLikes())
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .deleteAt(entity.getDeleteAt())
                .build();
    }
}
