package com.example.vegetabledragon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = true)
    private Double predictionScore;

    public Prediction(Post post, Double predictionScore) {
        this.post = post;
        this.predictionScore = predictionScore;
    }
}
