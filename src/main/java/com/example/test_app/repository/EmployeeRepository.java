package com.example.test_app.repository;

import com.example.test_app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.isDelete = 0")
    List<Employee> findAllActiveEmployees();

    @Query("SELECT e FROM Employee e WHERE e.idNumber = :idNumber")
    Optional<Employee> findByIdNumber(@org.springframework.data.repository.query.Param("idNumber") String idNumber);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND e.isDelete = 0")
    List<Employee> findActiveByNameContaining(@org.springframework.data.repository.query.Param("name") String name);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.idNumber = :idNumber AND (:id IS NULL OR e.id <> :id)")
    boolean existsByIdNumber(@org.springframework.data.repository.query.Param("idNumber") String idNumber,
            @org.springframework.data.repository.query.Param("id") Long id);

    @Query("UPDATE Employee e SET e.isDelete = 1 WHERE e.id = :id")
    @org.springframework.data.jpa.repository.Modifying
    void softDeleteById(@org.springframework.data.repository.query.Param("id") Long id);
}
