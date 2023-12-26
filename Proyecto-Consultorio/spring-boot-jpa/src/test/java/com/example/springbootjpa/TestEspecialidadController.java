package com.example.springbootjpa;

import com.example.springbootjpa.controllers.EspecialidadController;
import com.example.springbootjpa.controllers.PacienteController;
import com.example.springbootjpa.model.entities.Especialidad;
import com.example.springbootjpa.model.entities.Paciente;
import com.example.springbootjpa.services.EspecialidadService;
import com.example.springbootjpa.services.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EspecialidadController.class)
public class TestEspecialidadController {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EspecialidadService especialidadService;
    @Test
    public void testEspecialidadController() throws Exception {
        Especialidad especialidad = new Especialidad();
        doNothing().when(especialidadService).guardar(especialidad);

        mvc.perform(post("/guardar")
                        .flashAttr("especialidad", especialidad))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/especialidades"));;
    }

}
