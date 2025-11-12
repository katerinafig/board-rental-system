package com.example.boardrental.controllers;

import com.example.boardrental.models.RentalRequestDTO;
import com.example.boardrental.models.RentalResponseDTO;
import com.example.boardrental.services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentalController implements RentalControllerApi {
    private final RentalService rentalService;

    public ResponseEntity<RentalResponseDTO> createRental(@Valid RentalRequestDTO requestDTO) {
        return ResponseEntity.ok(rentalService.createRental(requestDTO));
    }

    public ResponseEntity<List<RentalResponseDTO>> getActiveRentals() {
        return ResponseEntity.ok(rentalService.getActiveRentals());
    }

    public ResponseEntity<Void> returnBoard(Long rentalId) {
        rentalService.returnBoard(rentalId);
        return ResponseEntity.ok().build();
    }
}