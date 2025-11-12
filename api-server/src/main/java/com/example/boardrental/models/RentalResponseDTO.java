package com.example.boardrental.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Ответ с информацией об аренде")
public record RentalResponseDTO(
        @Schema(description = "ID аренды", example = "1")
        Long id,

        @Schema(description = "Информация о борде")
        BoardDTO board,

        @Schema(description = "Имя клиента", example = "Иван Иванов")
        String customerName,

        @Schema(description = "Email клиента", example = "ivan@example.com")
        String customerEmail,

        @Schema(description = "Часы аренды", example = "3")
        Integer rentalHours,

        @Schema(description = "Общая стоимость", example = "46.5")
        Double totalPrice,

        @Schema(description = "Дата аренды")
        LocalDateTime rentalDate,

        @Schema(description = "Аренда активна", example = "true")
        Boolean isActive
) {
}
