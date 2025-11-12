package com.example.boardrental.models;

import com.example.boardrental.enums.BoardType;
import com.example.boardrental.enums.Condition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Борд")
public record BoardDTO(
        @Schema(description = "ID борда", example = "1")
        Long id,
        @Schema(description = "Название борда", example = "Pro Skateboard X1")
        String name,
        @Schema(description = "Тип борда", example = "SKATEBOARD")
        BoardType type,
        @Schema(description = "Длина в см", example = "80")
        Integer length,
        @Schema(description = "Состояние борда", example = "GOOD")
        Condition condition,
        @Schema(description = "Цена аренды в час", example = "15.5")
        Double pricePerHour,
        @Schema(description = "Доступен для аренды", example = "true")
        Boolean isAvailable
) {
}