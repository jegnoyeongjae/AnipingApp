package com.aniping.anipingapp.animation.repository;

import com.aniping.anipingapp.animation.entity.FileEntity;
import com.aniping.anipingapp.animation.entity.Status;
import com.aniping.anipingapp.animation.entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    // 핵심 기능: 특정 애니메이션(targetId)의 '활성화된' '애니메이션 타입' 이미지만 가져오기
    Optional<FileEntity> findByTargetIdAndTargetTypeAndStatus(
            Integer targetId,
            TargetType targetType,
            Status status
    );

    // 여러 장의 이미지가 있을 경우 리스트로 가져오기
    List<FileEntity> findAllByTargetIdAndTargetTypeAndStatus(
            Integer targetId,
            TargetType targetType,
            Status status
    );
}
