package com.example.boardrental.repositories;

import com.example.boardrental.entities.BoardEntity;
import com.example.boardrental.enums.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTypeAndIsAvailableTrue(BoardType type);
    List<BoardEntity> findByIsAvailableTrue();
}