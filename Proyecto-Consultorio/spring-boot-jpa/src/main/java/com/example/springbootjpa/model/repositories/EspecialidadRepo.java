package com.example.springbootjpa.model.repositories;

import com.example.springbootjpa.model.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepo extends JpaRepository<Especialidad, Long> {
}