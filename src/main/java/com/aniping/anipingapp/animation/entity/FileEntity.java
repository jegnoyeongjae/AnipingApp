package com.aniping.anipingapp.animation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TargetType targetType;

    private Integer targetId;

    private String s3Key;

    private String originalName;

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE, DELETED

    private Integer depth;

    @Column(name = "createAt", updatable = false, insertable = false)
    private LocalDateTime createAt;

    @Column(name = "updateAt", insertable = false)
    private LocalDateTime updateAt;

    @Column(name = "deleteAt", insertable = false)
    private LocalDateTime deleteAt;
}
