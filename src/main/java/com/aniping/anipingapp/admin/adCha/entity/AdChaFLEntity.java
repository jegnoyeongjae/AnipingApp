package com.aniping.anipingapp.admin.adCha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "famousline")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class AdChaFLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "charId")
    private Integer charId; // 어떤 캐릭터의 대사인지

    @Column(length = 500)
    private String content;

    private Integer likes;

    @Column(columnDefinition = "ENUM('waiting', 'accept', 'reject')")
    private String active; // pending 대신 테이블 스펙에 맞춤

    @Column(name = "userId")
    private Integer userId;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;
}
