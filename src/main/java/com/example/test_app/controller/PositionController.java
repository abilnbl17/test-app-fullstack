package com.example.test_app.controller;

import com.example.test_app.model.Position;
import com.example.test_app.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping
    public List<Position> getAllActivePositions() {
        return positionRepository.findAllActivePositions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {

        if (positionRepository.findActiveByCode(position.getCode()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Position savedPosition = positionRepository.save(position);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPosition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position positionDetails) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        if (optionalPosition.isPresent()) {
            Position existingPosition = optionalPosition.get();
            existingPosition.setCode(positionDetails.getCode());
            existingPosition.setName(positionDetails.getName());

            existingPosition.setIsDelete(positionDetails.getIsDelete());

            Position updatedPosition = positionRepository.save(existingPosition);
            return ResponseEntity.ok(updatedPosition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletePosition(@PathVariable Long id) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        if (optionalPosition.isPresent()) {
            Position positionToSoftDelete = optionalPosition.get();
            positionToSoftDelete.setIsDelete(1);
            positionRepository.save(positionToSoftDelete);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Position> searchPositions(@RequestParam String name) {
        return positionRepository.findActiveByNameContaining(name);
    }
}
