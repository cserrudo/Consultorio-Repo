package com.example.springbootjpa.services;

import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.entities.Turno;
import com.example.springbootjpa.model.repositories.PacienteRepo;
import com.example.springbootjpa.model.repositories.ProfesionalRepo;
import com.example.springbootjpa.model.repositories.TurnoRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


/*la clínica atenderá de 8 a 23hs de lunes a sábados
    tampoco si en un horario determinado el profesional no está disponible.*/


@Service
public class TurnoService {
  @Autowired
  private TurnoRepo tRepo;
  @Autowired
  private PacienteRepo pRepo;

  public boolean guardarTurno(Turno t){
    if(clinica_esta_abierta(t) && profesional_esta_disponible(t)){
      tRepo.save(t);
      return true;
    }else{
      return false;
    }
  }
  public boolean profesional_esta_disponible(Turno t){
    return t.getFecha_hora_turno().getHour() >= t.getProfesional().getHorario_inicio().getHour()
        && t.getFecha_hora_turno().getHour() <= t.getProfesional().getHorario_fin().getHour();
  }
  public boolean clinica_esta_abierta(Turno t){
    return t.getFecha_hora_turno().getHour() >= 8
        && t.getFecha_hora_turno().getHour() <= 23
        && es_dia_de_semana(t);
  }

  public boolean es_dia_de_semana(Turno t){
    DayOfWeek dia = t.getFecha_hora_turno().getDayOfWeek();

    return dia == DayOfWeek.MONDAY ||
        dia == DayOfWeek.TUESDAY ||
        dia == DayOfWeek.WEDNESDAY ||
        dia == DayOfWeek.THURSDAY ||
        dia == DayOfWeek.FRIDAY ||
        dia == DayOfWeek.SATURDAY;

  }

  public boolean se_puede_modificar(Turno t,LocalDateTime ahora){
    Duration duracion = Duration.between(t.getFecha_hora_turno(), ahora);

    if(ahora.getDayOfMonth() == t.getFecha_hora_turno().getDayOfMonth() &&
        ahora.getMonth() == t.getFecha_hora_turno().getMonth() &&
        ahora.getYear() == t.getFecha_hora_turno().getYear() && t.getFecha_hora_turno().isAfter(ahora)){

      return Math.abs(duracion.toMinutes())>= 60;
    }else{
      return true;
    }

  }

  public List<Turno> obtenerTurnos(){
    return tRepo.findAll();
  }

  public void borrarPorId(Long id){
    tRepo.deleteById(id);
  }

  public Turno buscarPorId(Long id){
    return tRepo.findById(id).get();
  }

  public List<Turno> buscarPacientePorId(Long idPaciente){
    List<Turno> turnos = Collections.emptyList();

    if(pRepo.existsById(idPaciente)){
      Paciente paciente = pRepo.findById(idPaciente).get();
       turnos = paciente.getTurnos();
    }

    return turnos;
  }
}
