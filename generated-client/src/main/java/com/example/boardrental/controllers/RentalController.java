package com.example.boardrental.controllers;

import com.example.boardrental.models.RentalRequestDTO;
import com.example.boardrental.models.RentalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalControllerApi api;

    @PostMapping
    public void createRental( @RequestBody RentalRequestDTO requestDTO) {
        api.createRental(requestDTO);
    }

    @GetMapping(produces = "application/json")
    public List<RentalResponseDTO> getActiveRentals() {
        return api.getActiveRentals();
    }

    @PostMapping("/{rentalId}/return")
    public void returnBoard(@PathVariable Long rentalId) {
        api.returnBoard(rentalId);
    }
}