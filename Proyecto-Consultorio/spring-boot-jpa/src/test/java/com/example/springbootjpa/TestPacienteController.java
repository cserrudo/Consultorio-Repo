package com.example.springbootjpa;

import com.example.springbootjpa.controllers.PacienteController;
import com.example.springbootjpa.controllers.TurnoController;
import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.model.entities.Turno;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
public class TestPacienteController {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PacienteService pacienteService;
    @Test
    public void testPacienteRegistro() throws Exception {
        Paciente paciente = new Paciente();
        doNothing().when(pacienteService).guardarPaciente(paciente);

        mvc.perform(post("/guardarPaciente")
                        .flashAttr("paciente", paciente))
                .andExpect(view().name("pacienteRegistro"));
    }

}
