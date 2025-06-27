package com.example.test_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "t2_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "is_delete", nullable = false)
    private Integer isDelete = 0;

    public Employee() {
    }

    public Employee(String name, LocalDate birthDate, Position position, String idNumber, Integer gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.idNumber = idNumber;
        this.gender = gender;
        this.isDelete = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", position=" + (position != null ? position.getName() : "null") +
                ", idNumber='" + idNumber + '\'' +
                ", gender=" + gender +
                ", isDelete=" + isDelete +
                '}';
    }
}