package com.example.boardrental.controllers;

import com.example.boardrental.enums.BoardType;
import com.example.boardrental.models.BoardDTO;
import com.example.boardrental.services.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@Tag(name = "Board Controller", description = "Управление спортивными бордами")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "Получить все доступные борды")
    @GetMapping(value = "/available", produces = "application/json")
    public List<BoardDTO> getAvailableBoards() {
        return boardService.getAvailableBoards();
    }

    @Operation(summary = "Получить доступные борды по типу")
    @GetMapping(value = "/available/{type}", produces = "application/json")
    public List<BoardDTO> getAvailableBoardsByType(@PathVariable BoardType type) {
        return boardService.getAvailableBoardsByType(type);
    }
}