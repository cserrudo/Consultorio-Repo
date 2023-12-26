package com.example.springbootjpa.services;


import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.repositories.EspecialidadRepo;
import com.example.springbootjpa.model.repositories.ProfesionalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProfesionalService {
    @Autowired
    private ProfesionalRepo pRepo;

    public void guardarProfesional(Profesional p){
        pRepo.save(p);
    }

    public List <Profesional> obtenerProfesionales(){
        return pRepo.findAll();
    }
}
