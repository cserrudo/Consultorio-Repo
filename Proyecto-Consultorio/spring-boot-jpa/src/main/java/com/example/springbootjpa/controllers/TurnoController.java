package com.example.springbootjpa.controllers;

import com.example.springbootjpa.model.entities.Consultorio;
import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.entities.Turno;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.ProfesionalService;
import com.example.springbootjpa.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TurnoController {
  @Autowired
  private TurnoService service;
  @Autowired
  private PacienteService pacienteService;
  @Autowired
  private ProfesionalService profesionalService;

  @GetMapping("/turno_registro")
  public String turnoRegistro(Model model){
      List<Paciente> pacientes = pacienteService.obtenerPaciente();
      List<Profesional> profesionales = profesionalService.obtenerProfesionales();

      model.addAttribute("profesionales", profesionales);
      model.addAttribute("pacientes", pacientes);
      model.addAttribute("consultorios", Consultorio.values());
      return "turnoRegistro";
  }

@GetMapping("/turnos")
public ModelAndView turnoListado(){
    List<Turno> list=service.obtenerTurnos();
    ModelAndView m = new ModelAndView();
    m.setViewName("turnos");
    m.addObject("turno",list);
    return m;
}
  @PostMapping("/guardarTurno")
  public String guardarTurno(@ModelAttribute Turno t, Model model){
    if (service.guardarTurno(t)) {
      Paciente paciente = t.getPaciente();
      paciente.getTurnos().add(t);
      return "redirect:/turnos";
    } else {
      List<Paciente> pacientes = pacienteService.obtenerPaciente();
      List<Profesional> profesionales = profesionalService.obtenerProfesionales();

      model.addAttribute("profesionales", profesionales);
      model.addAttribute("pacientes", pacientes);
      model.addAttribute("consultorios", Consultorio.values());

      model.addAttribute("error", "No se pudo guardar el turno");
      return "turnoRegistro";
    }
  }


  @RequestMapping("/borrarTurno/{id}")
  public ModelAndView borrarTurno(@PathVariable("id") Long id, Model model){
    Turno t = service.buscarPorId(id);
    ModelAndView m = new ModelAndView();
    if(!service.se_puede_modificar(t, LocalDateTime.now())){
      model.addAttribute("errorBorrar", "No se puede borrar el turno ya que falta menos de una hora");
    }else{
      service.borrarPorId(id);
    }
    List<Turno> list=service.obtenerTurnos();
    m.setViewName("turnos");
    m.addObject("turno",list);
    return m;
  }
  @RequestMapping("/editarTurno/{id}")
  public ModelAndView editarTurno(@PathVariable("id") Long id,Model model) {
    Turno t = service.buscarPorId(id);
    ModelAndView m = new ModelAndView();

    if(!service.se_puede_modificar(t, LocalDateTime.now())){
      List<Turno> list=service.obtenerTurnos();
      model.addAttribute("errorMod", "No se puede modificar el turno ya que falta menos de una hora");

      m.setViewName("turnos");
      m.addObject("turno",list);
    }else{
      m.setViewName("editarTurnoId");

      List<Paciente> pacientes = pacienteService.obtenerPaciente();
      List<Profesional> profesionales = profesionalService.obtenerProfesionales();

      model.addAttribute("profesionales", profesionales);
      model.addAttribute("pacientes", pacientes);
      model.addAttribute("consultorios", Consultorio.values());
      model.addAttribute("turno",t);
      model.addAttribute("consultorioSeleccionado", t.getConsultorio().name());
    }
    return m;
  }
  @GetMapping("/buscarTurno")
  public String obtenerIdPaciente() {
    return "buscarTurno";
  }

  @PostMapping("/buscarTurnoPorPaciente")
  public ModelAndView buscarPorId(@RequestParam("id") Long id, Model model) {
    List<Turno> turnos = service.buscarPacientePorId(id);
    ModelAndView m = new ModelAndView();
    if(turnos.isEmpty()) {
      model.addAttribute("errorBusqueda", "No hay turnos asociados para este id de paciente");
      m.setViewName("buscarTurno");

    }else{
      m.setViewName("turnos");
      m.addObject("turno",turnos);
    }
    return m;
  }





}
