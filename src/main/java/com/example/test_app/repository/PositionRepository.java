package com.example.test_app.repository;

import com.example.test_app.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM Position p WHERE p.isDelete = 0")
    List<Position> findAllActivePositions();

    @Query("SELECT p FROM Position p WHERE LOWER(p.code) = LOWER(:code) AND p.isDelete = 0")
    Optional<Position> findActiveByCode(@org.springframework.data.repository.query.Param("code") String code);

    @Query("SELECT p FROM Position p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.isDelete = 0")
    List<Position> findActiveByNameContaining(@org.springframework.data.repository.query.Param("name") String name);
}
