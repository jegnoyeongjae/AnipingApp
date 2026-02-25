package com.aniping.anipingapp.admin.adVoiceActor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "voiceactor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adVAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 100)
    private String name;

    @Column(length = 20)
    private String birth;

    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('A', 'B', 'AB', 'O', 'N')")
    private BloodType bloodType;

    @Column(length = 100)
    private String agency;

    @Column(name = "`rank`")
    private Integer rank;

    @Builder.Default
    private Integer likes = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    // Soft Delete를 위한 필드 (SQL의 ON UPDATE 옵션을 고려)
    private LocalDateTime deleteAt;

    public enum BloodType {
        A, B, AB, O, N
    }
}
