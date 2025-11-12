package com.example.boardrental.controllers;

import com.example.boardrental.models.RentalRequestDTO;
import com.example.boardrental.models.RentalResponseDTO;
import com.example.boardrental.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
@Tag(name = "Rental API", description = "Управление арендой бордов")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @Operation(summary = "Создать новую аренду")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDTO createRental(@Valid @RequestBody RentalRequestDTO requestDTO) {
        return rentalService.createRental(requestDTO);
    }

    @Operation(summary = "Получить активные аренды")
    @GetMapping
    public List<RentalResponseDTO> getActiveRentals() {
        return rentalService.getActiveRentals();
    }

    @Operation(summary = "Вернуть борд")
    @PostMapping("/{rentalId}/return")
    public void returnBoard(@PathVariable Long rentalId) {
        rentalService.returnBoard(rentalId);
    }
}