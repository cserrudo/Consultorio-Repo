package com.example.springbootjpa.model.repositories;

import com.example.springbootjpa.model.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepo extends JpaRepository<Turno, Long> {
}