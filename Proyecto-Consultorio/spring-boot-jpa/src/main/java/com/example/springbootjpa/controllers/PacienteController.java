package com.example.springbootjpa.controllers;

import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.services.EspecialidadService;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class PacienteController {
  @Autowired
  private PacienteService service;

  @GetMapping("/paciente_registro")
  public String pacienteRegistro(){
    return "pacienteRegistro";
  }

/*@GetMapping("/pacientes")
  public ModelAndView pacienteListado(){
    List<Paciente> list=service.obtenerPaciente();
    ModelAndView m = new ModelAndView();
    m.setViewName("pacientes");
    m.addObject("paciente",list);
    return m;
}*/

  @PostMapping("/guardarPaciente")
  public String guardarPaciente(@ModelAttribute Paciente p, Model modelo){
    service.guardarPaciente(p);
    modelo.addAttribute("msg", "Paciente guardado!");
    return "pacienteRegistro";
  }


}
