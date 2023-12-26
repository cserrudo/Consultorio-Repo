package com.example.springbootjpa.controllers;

import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.services.EspecialidadService;
import com.example.springbootjpa.services.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class ProfesionalesController {
    @Autowired
    private ProfesionalService service;
    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping("/profesional_registro")
    public String profesionalRegistro(Model model){

        List<Especialidad> especialidades = especialidadService.obtenerEspecialidades();

        model.addAttribute("especialidades", especialidades);

        return "profesionalRegistro";
    }

    @GetMapping("/profesionales")
    public ModelAndView especialidadListado(){
        List<Profesional> list=service.obtenerProfesionales();
        ModelAndView m = new ModelAndView();
        m.setViewName("profesionales");
        m.addObject("profesional",list);
        return m;
    }

    @PostMapping("/guardarProfesional")
    public String guardarProfesional(@ModelAttribute Profesional p){

        Especialidad especialidad = p.getEspecialidad();

        especialidad.getProfesionales().add(p);

        service.guardarProfesional(p);

        return "redirect:/profesionales";
    }



}
