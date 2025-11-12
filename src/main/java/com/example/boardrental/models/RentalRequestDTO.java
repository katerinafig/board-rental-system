package com.example.boardrental.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Аренда борда")
public record RentalRequestDTO(
        @Schema(description = "ID борда", example = "1")
        @NotNull(message = "ID борда обязательно")
        Long boardId,

        @Schema(description = "Имя клиента", example = "Иван Иванов")
        @NotBlank(message = "Имя клиента обязательно")
        String customerName,

        @Schema(description = "Email клиента", example = "ivan@example.com")
        @NotBlank(message = "Email клиента обязателен")
        @Email(message = "Некорректный формат email")
        String customerEmail,

        @Schema(description = "Часы аренды", example = "3")
        @NotNull(message = "Количество часов обязательно")
        @Min(value = 1, message = "Минимальное время аренды - 1 час")
        Integer rentalHours
) {
}