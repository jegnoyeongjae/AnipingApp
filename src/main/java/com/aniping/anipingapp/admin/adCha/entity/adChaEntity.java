package com.aniping.anipingapp.admin.adCha.entity;

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

    @CreationTimestamp
    @Column(name = "createAt", updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "deleteAt")
    private LocalDateTime deleteAt;
}
