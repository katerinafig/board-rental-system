package com.example.boardrental.controllers;

import com.example.boardrental.models.BoardDTO.TypeEnum;
import com.example.boardrental.models.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardControllerApi api;

    @GetMapping(value = "/available", produces = "application/json")
    public List<BoardDTO> getAvailableBoards() {
        return api.getAvailableBoards();
    }

    @GetMapping(value = "/available/{type}", produces = "application/json")
    public List<BoardDTO> getAvailableBoardsByType(@PathVariable TypeEnum type) {
        return api.getAvailableBoardsByType(type.getValue());
    }
}