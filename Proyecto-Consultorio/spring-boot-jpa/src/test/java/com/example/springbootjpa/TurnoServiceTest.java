package com.example.springbootjpa;

import com.example.springbootjpa.model.entities.Consultorio;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.entities.Turno;
import com.example.springbootjpa.model.repositories.PacienteRepo;
import com.example.springbootjpa.model.repositories.TurnoRepo;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SpringBootTest

public class TurnoServiceTest {
    @Mock
    private TurnoRepo turnoRepo;
    @Mock
    private PacienteRepo pacienteRepo;

    @InjectMocks
    private TurnoService turnoService;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    public void testGuardarTurnoExitoso() {
        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 26,20, 0, 0));

        Profesional profesional = new Profesional();
        profesional.setHorario_inicio(LocalTime.of(10,0));
        profesional.setHorario_fin(LocalTime.of(22,0));
        Paciente paciente = new Paciente();

        turno.setProfesional(profesional);
        turno.setPaciente(paciente);

        assertTrue(turnoService.guardarTurno(turno));
    }


    @Test
    public void testGuardarTurnoEnDomingo() {
        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 31,20, 0, 0));

        Profesional profesional = new Profesional();
        profesional.setHorario_inicio(LocalTime.of(10,0));
        profesional.setHorario_fin(LocalTime.of(22,0));
        Paciente paciente = new Paciente();

        turno.setProfesional(profesional);
        turno.setPaciente(paciente);
        assertFalse(turnoService.guardarTurno(turno));
    }

    @Test
    public void testGuardarTurnoFueraDeHorarioMedico() {
        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 26,23, 0, 0));

        Profesional profesional = new Profesional();
        profesional.setHorario_inicio(LocalTime.of(10,0));
        profesional.setHorario_fin(LocalTime.of(22,0));
        Paciente paciente = new Paciente();

        turno.setProfesional(profesional);
        turno.setPaciente(paciente);

        assertFalse(turnoService.guardarTurno(turno));
    }

    @Test
    public void testGuardarTurnoFueraDeHorarioClinica(){
        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 26,3, 0, 0));

        Profesional profesional = new Profesional();
        profesional.setHorario_inicio(LocalTime.of(2,0));
        profesional.setHorario_fin(LocalTime.of(22,0));
        Paciente paciente = new Paciente();

        turno.setProfesional(profesional);
        turno.setPaciente(paciente);

        assertFalse(turnoService.guardarTurno(turno));
    }

   @Test
    public void testSePuedeModificarMasDeUnaHoraAntes() {
        LocalDateTime ahora = LocalDateTime.of(2023, 12, 26, 2, 0, 0);

        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 26, 3, 0, 0));

        boolean resultado = turnoService.se_puede_modificar(turno, ahora);

        assertTrue(resultado);
    }
    @Test
    public void testNoSePuedeModificarMenosDeUnaHoraAntes() {
        LocalDateTime ahora = LocalDateTime.of(2023, 12, 26, 2, 0, 0);

        Turno turno = new Turno();
        turno.setFecha_hora_turno(LocalDateTime.of(2023, 12, 26, 2, 59, 0));

        boolean resultado = turnoService.se_puede_modificar(turno, ahora);

        assertFalse(resultado);
    }
}



