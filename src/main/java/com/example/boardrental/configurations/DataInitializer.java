package com.example.boardrental.configurations;

import com.example.boardrental.entities.BoardEntity;
import com.example.boardrental.enums.BoardType;
import com.example.boardrental.enums.Condition;
import com.example.boardrental.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BoardRepository boardRepository;

    @Override
    public void run(String... args) throws Exception {
        // Инициализация тестовых данных
        boardRepository.save(new BoardEntity(
                "Pro Skateboard X1",
                BoardType.SKATEBOARD,
                80,
                Condition.GOOD,
                600.0,
                true
        ));

        boardRepository.save(new BoardEntity(
                "Long Cruiser 100",
                BoardType.LONGBOARD,
                100,
                Condition.NEW,
                400.0,
                true
        ));

        boardRepository.save(new BoardEntity(
                "Wave Rider Surf",
                BoardType.SURFBOARD,
                180,
                Condition.USED,
                750.0,
                true));

        boardRepository.save(new BoardEntity(
                "Snow Pro 150",
                BoardType.SNOWBOARD,
                150,
                Condition.GOOD,
                500.0,
                true));

        boardRepository.save(new BoardEntity(
                "Street King",
                BoardType.SKATEBOARD,
                75,
                Condition.NEW,
                350.0,
                true));
    }
}