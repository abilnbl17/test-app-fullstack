package com.example.test_app.controller;

import com.example.test_app.model.Employee;
import com.example.test_app.model.Position;
import com.example.test_app.repository.EmployeeRepository;
import com.example.test_app.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    private boolean isValidIdNumber(String idNumber) {
        return idNumber != null && idNumber.matches("\\d+");
    }

    @GetMapping
    public List<Employee> getAllActiveEmployees() {
        return employeeRepository.findAllActiveEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String name) {
        return employeeRepository.findActiveByNameContaining(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {

        if (!isValidIdNumber(employee.getIdNumber())) {
            return ResponseEntity.badRequest().body("NIP harus berisi angka.");
        }

        if (employeeRepository.existsByIdNumber(employee.getIdNumber(), null)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NIP sudah ada di database.");
        }

        Optional<Position> position = positionRepository.findById(employee.getPosition().getId());
        if (position.isEmpty()) {
            return ResponseEntity.badRequest().body("ID Jabatan tidak valid.");
        }
        employee.setPosition(position.get());

        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();

            if (!isValidIdNumber(employeeDetails.getIdNumber())) {
                return ResponseEntity.badRequest().body("NIP harus berisi angka.");
            }

            if (employeeRepository.existsByIdNumber(employeeDetails.getIdNumber(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("NIP sudah ada di database untuk karyawan lain.");
            }
            Optional<Position> position = positionRepository.findById(employeeDetails.getPosition().getId());
            if (position.isEmpty()) {
                return ResponseEntity.badRequest().body("ID Jabatan tidak valid.");
            }

            existingEmployee.setName(employeeDetails.getName());
            existingEmployee.setBirthDate(employeeDetails.getBirthDate());
            existingEmployee.setPosition(position.get());
            existingEmployee.setIdNumber(employeeDetails.getIdNumber());
            existingEmployee.setGender(employeeDetails.getGender());
            existingEmployee.setIsDelete(employeeDetails.getIsDelete());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.softDeleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/positions-for-dropdown")
    public List<Position> getPositionsForDropdown() {
        return positionRepository.findAllActivePositions();
    }
}