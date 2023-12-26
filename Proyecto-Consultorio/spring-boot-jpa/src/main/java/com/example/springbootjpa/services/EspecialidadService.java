package com.example.springbootjpa.services;

import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.repositories.EspecialidadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EspecialidadService {
    @Autowired
    private EspecialidadRepo eRepo;

    public void guardar(Especialidad e){
         eRepo.save(e);
    }

    public List <Especialidad> obtenerEspecialidades(){
        return eRepo.findAll();
    }
}
