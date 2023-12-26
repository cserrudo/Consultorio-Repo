package com.example.springbootjpa;

import com.example.springbootjpa.controllers.PacienteController;
import com.example.springbootjpa.controllers.ProfesionalesController;
import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.model.entities.Profesional;
import com.example.springbootjpa.services.EspecialidadService;
import com.example.springbootjpa.services.PacienteService;
import com.example.springbootjpa.services.ProfesionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfesionalesController.class)
public class TestProfesionalController {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProfesionalService profesionalService;
    @MockBean
    private EspecialidadService especialidadService;
    @Test
    public void testProfesionalRegistro() throws Exception {
        Profesional profesional = new Profesional();
        when(especialidadService.obtenerEspecialidades()).thenReturn(Collections.emptyList());

        Especialidad especialidad = new Especialidad();
        profesional.setEspecialidad(especialidad);
        doNothing().when(profesionalService).guardarProfesional(profesional);

        mvc.perform(post("/guardarProfesional")
                        .flashAttr("profesional", profesional))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profesionales"));;
    }
}