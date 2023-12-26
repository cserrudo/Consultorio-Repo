package com.example.springbootjpa;

import com.example.springbootjpa.controllers.TurnoController;
import com.example.springbootjpa.model.entities.*;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.ProfesionalService;
import com.example.springbootjpa.services.TurnoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@WebMvcTest(TurnoController.class)
public class TestTurnoController {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private PacienteService pacienteService;
  @MockBean
  private ProfesionalService profesionalService;
  @MockBean
  private TurnoService turnoService;

  @Test
  public void testGuardarTurnoExitoso() throws Exception {
   Turno turnoValido = new Turno();
    turnoValido.setPaciente(new Paciente());
    turnoValido.setProfesional(new Profesional());
    Mockito.when(turnoService.guardarTurno(turnoValido)).thenReturn(true);

    mvc.perform(post("/guardarTurno")
            .flashAttr("turno", turnoValido))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/turnos"));
  }

  @Test
  public void testGuardarTurnoFallido() throws Exception {
    Turno turnoInvalido = new Turno();

    Mockito.when(turnoService.guardarTurno(turnoInvalido)).thenReturn(false);
    mvc.perform(post("/guardarTurno")
            .flashAttr("turno", turnoInvalido)
        )
        .andExpect(model().attributeExists("error"))
        .andExpect(view().name("turnoRegistro"));

  }

  @Test
  public void testNoSePuedeModificarTurnoConHoraDeHoyConMenosDeUnaHora() throws Exception {
    Turno turno = new Turno();
    turno.setId(1L);

    Mockito.when(turnoService.buscarPorId(1L)).thenReturn(turno);

    Mockito.when(turnoService.se_puede_modificar(turno, LocalDateTime.now())).thenReturn(false);

    mvc.perform(get("/editarTurno/1"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("errorMod"));
  }

  @Test
  public void testSePuedeBorrar() throws Exception {
    Turno turno = new Turno();
    turno.setId(1L);

    Mockito.when(turnoService.buscarPorId(1L)).thenReturn(turno);

    Mockito.when(turnoService.se_puede_modificar(turno, LocalDateTime.now())).thenReturn(true);

    mvc.perform(get("/borrarTurno/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("turnos"));
  }
  @Test
  public void testNoSePuedeBorrar() throws Exception {
    Turno turno = new Turno();
    turno.setId(1L);

    Mockito.when(turnoService.buscarPorId(1L)).thenReturn(turno);

    Mockito.when(turnoService.se_puede_modificar(turno, LocalDateTime.now())).thenReturn(false);

    mvc.perform(get("/borrarTurno/1"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("errorBorrar"));
  }
  @Test
  public void testTurnoListado() throws Exception {
    Especialidad e = new Especialidad();
    e.setId(1L);

    Paciente paciente = new Paciente();
    paciente.setId(1L);

    Profesional profesional = new Profesional();
    profesional.setId(1L);
    profesional.setEspecialidad(e);

    Turno turno = new Turno();
    turno.setPaciente(paciente);
    turno.setProfesional(profesional);

    List<Turno> turnos = Arrays.asList(turno);

    when(turnoService.obtenerTurnos()).thenReturn(turnos);

    mvc.perform(get("/turnos"))
            .andExpect(status().isOk())
            .andExpect(view().name("turnos"))
            .andExpect(model().attribute("turno", turnos));
  }


  @Test
  public void testBuscarTurnoPorPaciente() throws Exception {
    Especialidad e = new Especialidad();
    e.setId(1L);

    Paciente paciente = new Paciente();
    paciente.setId(1L);

    Profesional profesional = new Profesional();
    profesional.setId(1L);
    profesional.setEspecialidad(e);

    Turno turno = new Turno();
    turno.setPaciente(paciente);
    turno.setProfesional(profesional);

    List<Turno> turnos = Arrays.asList(turno);

    when(turnoService.buscarPacientePorId(1L)).thenReturn(turnos);

    mvc.perform(post("/buscarTurnoPorPaciente")
                    .param("id", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("turnos"))
            .andExpect(model().attribute("turno", turnos));
  }


  @Test
  public void testBuscarTurnoPorPacienteError() throws Exception {
    Especialidad e = new Especialidad();
    e.setId(1L);

    Paciente paciente = new Paciente();
    paciente.setId(1L);

    Profesional profesional = new Profesional();
    profesional.setId(1L);
    profesional.setEspecialidad(e);

    Turno turno = new Turno();
    turno.setPaciente(paciente);
    turno.setProfesional(profesional);

    List<Turno> turnos = Collections.emptyList();

    when(turnoService.buscarPacientePorId(1L)).thenReturn(turnos);

    mvc.perform(post("/buscarTurnoPorPaciente")
                    .param("id", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("buscarTurno"))
            .andExpect(model().attributeExists("errorBusqueda"));
  }


}