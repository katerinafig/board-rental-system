package com.example.boardrental.mappers;

import com.example.boardrental.entities.BoardEntity;
import com.example.boardrental.models.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    BoardDTO toDTO(BoardEntity entity);
    BoardEntity toEntity(BoardDTO dto);
}