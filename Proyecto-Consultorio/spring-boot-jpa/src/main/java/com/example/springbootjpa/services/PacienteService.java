package com.example.springbootjpa.services;

import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.repositories.PacienteRepo;
import com.example.springbootjpa.model.repositories.ProfesionalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
  @Autowired
  private PacienteRepo pRepo;
  public void guardarPaciente(Paciente p){
    pRepo.save(p);
  }

  public List<Paciente> obtenerPaciente(){
    return pRepo.findAll();
  }
}
