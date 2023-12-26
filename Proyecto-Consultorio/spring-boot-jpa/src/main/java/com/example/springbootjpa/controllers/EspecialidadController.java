package com.example.springbootjpa.controllers;

import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.services.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EspecialidadController {
    @Autowired
    private EspecialidadService service;

    @GetMapping("/especialidad_registro")
    public String especialidadRegistro(){
        return "especialidadRegistro";
    }

    @GetMapping("/especialidades")
    public ModelAndView especialidadListado(){
        List<Especialidad> list=service.obtenerEspecialidades();
        ModelAndView m = new ModelAndView();
        m.setViewName("especialidades");
        m.addObject("especialidad",list);
        return m;
    }
    @PostMapping ("/guardar")
    public String guardarEspecialidad(@ModelAttribute Especialidad e){
        service.guardar(e);
        return "redirect:/especialidades";
    }
}
