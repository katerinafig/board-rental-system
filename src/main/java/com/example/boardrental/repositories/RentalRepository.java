package com.example.boardrental.repositories;

import com.example.boardrental.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    List<RentalEntity> findByIsActiveTrue();
}