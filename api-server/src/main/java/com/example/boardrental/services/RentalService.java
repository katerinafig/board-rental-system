package com.example.boardrental.services;

import com.example.boardrental.entities.BoardEntity;
import com.example.boardrental.entities.RentalEntity;
import com.example.boardrental.mappers.RentalMapper;
import com.example.boardrental.models.RentalRequestDTO;
import com.example.boardrental.models.RentalResponseDTO;
import com.example.boardrental.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BoardService boardService;
    private final RentalMapper rentalMapper;

    @Transactional
    public RentalResponseDTO createRental(RentalRequestDTO requestDTO) {
        BoardEntity board = boardService.getBoardById(requestDTO.boardId());

        if (!board.getIsAvailable()) {
            throw new RuntimeException("Борд уже арендован");
        }

        Double totalPrice = board.getPricePerHour() * requestDTO.rentalHours();

        // Создание аренды
        RentalEntity rental = rentalMapper.toEntity(requestDTO);
        rental.setBoard(board);
        rental.setTotalPrice(totalPrice);

        // Помечаем борд как занятый
        board.setIsAvailable(false);

        RentalEntity savedRental = rentalRepository.save(rental);
        return rentalMapper.toResponseDTO(savedRental);
    }

    public List<RentalResponseDTO> getActiveRentals() {
        return rentalRepository.findByIsActiveTrue()
                .stream()
                .map(rentalMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void returnBoard(Long rentalId) {
        RentalEntity rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Аренда не найдена"));

        rental.setIsActive(false);
        rental.getBoard().setIsAvailable(true);

        rentalRepository.save(rental);
    }
}