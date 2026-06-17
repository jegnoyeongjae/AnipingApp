package com.aniping.anipingapp.admin.adTags.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdTagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // @Column(name = "cateId") 가 없는 것을 다시 확인

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String slug;

    @Column(nullable = false)
    private int sequence;

    public void changeSequence(int newSequence) {
        this.sequence = newSequence;
    }
}
