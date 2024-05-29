package com.habitlife.habitlifeapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habitlife.habitlifeapi.model.dto.UsuarioRequestDTO;
import com.habitlife.habitlifeapi.model.dto.UsuarioResponseDTO;
import com.habitlife.habitlifeapi.service.UsuarioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetUserByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Chavez");
        usuarioRequestDTO.setEmail("Juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(40);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Arequipa 1504");
        usuarioRequestDTO.setTelefono("998765201");

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(usuarioRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Chavez");
        usuarioRequestDTO.setEmail("Juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(40);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Arequipa 1504");
        usuarioRequestDTO.setTelefono("998765201");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(userId);
        usuarioResponseDTO.setNombre(usuarioRequestDTO.getNombre());
        usuarioResponseDTO.setApellidoPaterno(usuarioRequestDTO.getApellidoPaterno());
        usuarioResponseDTO.setApellidoMaterno(usuarioRequestDTO.getApellidoMaterno());
        usuarioResponseDTO.setEmail(usuarioRequestDTO.getEmail());
        usuarioResponseDTO.setAnios(usuarioRequestDTO.getAnios());
        usuarioResponseDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioResponseDTO.setDireccion(usuarioRequestDTO.getDireccion());
        usuarioResponseDTO.setTelefono(usuarioRequestDTO.getTelefono());

        when(usuarioService.updateAccount(anyLong(), any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nombre").value(usuarioRequestDTO.getNombre()))
                .andExpect(jsonPath("$.apellidoPaterno").value(usuarioRequestDTO.getApellidoPaterno()))
                .andExpect(jsonPath("$.apellidoMaterno").value(usuarioRequestDTO.getApellidoMaterno()))
                .andExpect(jsonPath("$.email").value(usuarioRequestDTO.getEmail()))
                .andExpect(jsonPath("$.anios").value(usuarioRequestDTO.getAnios()))
                .andExpect(jsonPath("$.direccion").value(usuarioRequestDTO.getDireccion()))
                .andExpect(jsonPath("$.telefono").value(usuarioRequestDTO.getTelefono()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        doNothing().when(usuarioService).deleteUser(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/{id}", userId))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deleteUser(userId);
    }

    @Test
    public void testAsignarProfesional() throws Exception {
        Long userId = 1L;
        Long profesionalId = 2L;

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(userId);
        usuarioResponseDTO.setNombre("Juan");
        usuarioResponseDTO.setApellidoPaterno("Perez");
        usuarioResponseDTO.setApellidoMaterno("Chavez");
        usuarioResponseDTO.setEmail("Juan@gmail.com");
        usuarioResponseDTO.setAnios(40);
        usuarioResponseDTO.setObjetivos(null); // O lo que corresponda según tu implementación
        usuarioResponseDTO.setDireccion("Av. Arequipa 1504");
        usuarioResponseDTO.setTelefono("998765201");

        when(usuarioService.asignarProfesional(anyLong(), anyLong())).thenReturn(usuarioResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/{id}/asignar-profesional/{profesionalId}", userId, profesionalId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellidoPaterno").value("Perez"))
                .andExpect(jsonPath("$.apellidoMaterno").value("Chavez"))
                .andExpect(jsonPath("$.email").value("Juan@gmail.com"))
                .andExpect(jsonPath("$.anios").value(40))
                .andExpect(jsonPath("$.direccion").value("Av. Arequipa 1504"))
                .andExpect(jsonPath("$.telefono").value("998765201"));
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
