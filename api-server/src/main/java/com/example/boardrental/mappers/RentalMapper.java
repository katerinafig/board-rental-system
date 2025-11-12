package com.example.boardrental.mappers;

import com.example.boardrental.entities.RentalEntity;
import com.example.boardrental.models.RentalRequestDTO;
import com.example.boardrental.models.RentalResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BoardMapper.class})
public interface RentalMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "board", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "rentalDate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    RentalEntity toEntity(RentalRequestDTO dto);

    RentalResponseDTO toResponseDTO(RentalEntity entity);
}