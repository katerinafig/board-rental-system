package com.example.boardrental.entities;

import com.example.boardrental.enums.BoardType;
import com.example.boardrental.enums.Condition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType type;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    public BoardEntity(String name, BoardType type, Integer length, Condition condition, Double pricePerHour, Boolean isAvailable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.condition = condition;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }
}