package com.aniping.anipingapp.admin.adAni.entity;

import com.aniping.anipingapp.admin.adTags.entity.AdTagsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anilist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdAniEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 200)
    private String title;

    @Column(length = 100)
    private String director;

    @Column(length = 100)
    private String studio;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    @Column(nullable = false)
    private String grade;

    @Column(name = "aniPv", columnDefinition = "TEXT")
    private String aniPv;
    
    @Builder.Default
    @Column(name = "viewCount")
    private Integer viewCount = 0;

    @Builder.Default
    @Column(name = "likes")
    private Integer likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateId", insertable = false, updatable = false)
    private AdTagsEntity category;
    
    @Column(name = "cateId")
    private Integer cateId;

    @CreationTimestamp
    @Column(name = "createAt", updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "deleteAt")
    private LocalDateTime deleteAt;
}
