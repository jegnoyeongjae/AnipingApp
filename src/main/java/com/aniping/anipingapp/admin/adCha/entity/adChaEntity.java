package com.aniping.anipingapp.admin.adCha.entity;

import com.aniping.anipingapp.admin.adAni.entity.AdAniEntity;
import com.aniping.anipingapp.user.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adChaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aniId")
    private Integer aniId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aniId", insertable = false, updatable = false)
    private AdAniEntity animation;

    @Column(name = "cvId")
    private Integer cvId;

    @Column(length = 100)
    private String name;

    @Builder.Default
    @Column(name = "voteCount")
    private Integer voteCount = 0;

    @Column(columnDefinition = "ENUM('waiting', 'accept', 'reject')")
    private String active;

    @Column(name = "userId")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "createAt", updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "deleteAt")
    private LocalDateTime deleteAt;
}
