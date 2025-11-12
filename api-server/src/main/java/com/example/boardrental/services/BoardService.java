package com.example.boardrental.services;

import com.example.boardrental.entities.BoardEntity;
import com.example.boardrental.enums.BoardType;
import com.example.boardrental.mappers.BoardMapper;
import com.example.boardrental.models.BoardDTO;
import com.example.boardrental.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public List<BoardDTO> getAvailableBoards() {
        return boardRepository.findByIsAvailableTrue()
                .stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BoardDTO> getAvailableBoardsByType(BoardType type) {
        return boardRepository.findByTypeAndIsAvailableTrue(type)
                .stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BoardEntity getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Борд не найден"));
    }
}