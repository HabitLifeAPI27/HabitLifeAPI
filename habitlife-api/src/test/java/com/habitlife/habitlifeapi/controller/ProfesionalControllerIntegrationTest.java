package com.habitlife.habitlifeapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habitlife.habitlifeapi.model.dto.ProfesionalRequestDTO;
import com.habitlife.habitlifeapi.model.dto.ProfesionalResponseDTO;
import com.habitlife.habitlifeapi.service.ProfesionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfesionalControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesionalService profesionalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProfesionals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profesionales"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProfesionalByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profesionales/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAssignedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profesionales/{id}/usuarios", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateProfesional() throws Exception {
        ProfesionalRequestDTO profesionalRequestDTO = new ProfesionalRequestDTO();
        profesionalRequestDTO.setNombre("Juan");
        profesionalRequestDTO.setApellidoPaterno("Perez");
        profesionalRequestDTO.setApellidoMaterno("Chavez");
        profesionalRequestDTO.setEmail("Juan@gmail.com");
        profesionalRequestDTO.setContrasena("12345678");
        profesionalRequestDTO.setAnios(40);
        profesionalRequestDTO.setTelefono("998765201");
        profesionalRequestDTO.setEspecialidad("Nutricionista");

        mockMvc.perform(MockMvcRequestBuilders.post("/profesionales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(profesionalRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProfesional() throws Exception {
        Long userId = 1L;

        ProfesionalRequestDTO profesionalRequestDTO = new ProfesionalRequestDTO();
        profesionalRequestDTO.setNombre("Maria");
        profesionalRequestDTO.setApellidoPaterno("Perez");
        profesionalRequestDTO.setApellidoMaterno("Gutierrez");
        profesionalRequestDTO.setEmail("maria@gmail.com");
        profesionalRequestDTO.setContrasena("123456789");
        profesionalRequestDTO.setAnios(40);
        profesionalRequestDTO.setTelefono("998765201");
        profesionalRequestDTO.setEspecialidad("Entrenador");

        ProfesionalResponseDTO profesionalResponseDTO = new ProfesionalResponseDTO();
        profesionalResponseDTO.setId(userId);
        profesionalResponseDTO.setNombre(profesionalRequestDTO.getNombre());
        profesionalResponseDTO.setApellidoPaterno(profesionalRequestDTO.getApellidoPaterno());
        profesionalResponseDTO.setApellidoMaterno(profesionalRequestDTO.getApellidoMaterno());
        profesionalResponseDTO.setEmail(profesionalRequestDTO.getEmail());
        profesionalResponseDTO.setAnios(profesionalRequestDTO.getAnios());
        profesionalResponseDTO.setTelefono(profesionalRequestDTO.getTelefono());
        profesionalResponseDTO.setEspecialidad(profesionalRequestDTO.getEspecialidad());

        when(profesionalService.updateProfessional(anyLong(), any(ProfesionalRequestDTO.class))).thenReturn(profesionalResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/profesionales/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(profesionalRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nombre").value(profesionalRequestDTO.getNombre()))
                .andExpect(jsonPath("$.apellidoPaterno").value(profesionalRequestDTO.getApellidoPaterno()))
                .andExpect(jsonPath("$.apellidoMaterno").value(profesionalRequestDTO.getApellidoMaterno()))
                .andExpect(jsonPath("$.email").value(profesionalRequestDTO.getEmail()))
                .andExpect(jsonPath("$.anios").value(profesionalRequestDTO.getAnios()))
                .andExpect(jsonPath("$.telefono").value(profesionalRequestDTO.getTelefono()))
                .andExpect(jsonPath("$.especialidad").value(profesionalRequestDTO.getEspecialidad()));
    }

    @Test
    public void testDeleteProfesional() throws Exception {
        Long profesionalId = 1L;

        doNothing().when(profesionalService).deleteProfesional(profesionalId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/profesionales/{id}", profesionalId))
                .andExpect(status().isNoContent());

        verify(profesionalService, times(1)).deleteProfesional(profesionalId);
    }

    // Método auxiliar para convertir objetos a JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
