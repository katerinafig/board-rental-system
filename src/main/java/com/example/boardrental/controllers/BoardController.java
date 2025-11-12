package com.example.boardrental.controllers;

import com.example.boardrental.enums.BoardType;
import com.example.boardrental.models.BoardDTO;
import com.example.boardrental.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController implements BoardControllerApi {
    private final BoardService boardService;

    public ResponseEntity<List<BoardDTO>> getAvailableBoards() {
        return ResponseEntity.ok(boardService.getAvailableBoards());
    }

    public ResponseEntity<List<BoardDTO>> getAvailableBoardsByType(@PathVariable BoardType type) {
        return ResponseEntity.ok(boardService.getAvailableBoardsByType(type));
    }
}