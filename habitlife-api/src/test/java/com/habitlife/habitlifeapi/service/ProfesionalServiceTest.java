package com.habitlife.habitlifeapi.service;

import com.habitlife.habitlifeapi.exception.ResourceDuplicateException;
import com.habitlife.habitlifeapi.exception.ResourceNotFoundException;
import com.habitlife.habitlifeapi.mapper.ProfesionalMapper;
import com.habitlife.habitlifeapi.mapper.UsuarioMapper;
import com.habitlife.habitlifeapi.model.dto.ProfesionalRequestDTO;
import com.habitlife.habitlifeapi.model.dto.ProfesionalResponseDTO;
import com.habitlife.habitlifeapi.model.dto.UsuarioResponseDTO;
import com.habitlife.habitlifeapi.model.entity.Profesional;
import com.habitlife.habitlifeapi.model.entity.Usuario;
import com.habitlife.habitlifeapi.repository.ProfesionalRepository;
import com.habitlife.habitlifeapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfesionalServiceTest {
    @Mock
    private ProfesionalRepository profesionalRepository;

    @Mock
    private ProfesionalMapper profesionalMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private ProfesionalService profesionalService;

    @Test
    public void testgetAllProfesionals(){
        Profesional profesional1 = new Profesional();
        profesional1.setId(1L);
        Profesional profesional2 = new Profesional();
        profesional2.setId(2L);
        List<Profesional> profesionals = Arrays.asList(profesional1, profesional2);

        when(profesionalRepository.findAll()).thenReturn(profesionals);

        ProfesionalResponseDTO profesionalResponseDTO = new ProfesionalResponseDTO();
        profesionalResponseDTO.setId(profesional1.getId());
        ProfesionalResponseDTO profesionalResponseDTO1 = new ProfesionalResponseDTO();
        profesionalResponseDTO1.setId(profesional2.getId());

        List<ProfesionalResponseDTO> expectedResponse = Arrays.asList(profesionalResponseDTO, profesionalResponseDTO1);
        when(profesionalMapper.convertToListDTO(profesionals)).thenReturn(expectedResponse);

        //Act
        List<ProfesionalResponseDTO> result =profesionalService.getAllProfesionals();

        //Assert
        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        // Verify that repository and mapper methods were called
        verify(profesionalRepository, times(1)).findAll();
        verify(profesionalMapper, times(1)).convertToListDTO(profesionals);
    }

    @Test
    public void testGetProfesionalById_ProfesionalExistente() {
        Profesional profesional1 = new Profesional();
        profesional1.setId(1L);
        ProfesionalResponseDTO profesionalResponseDTO1 = new ProfesionalResponseDTO();
        profesionalResponseDTO1.setId(profesional1.getId());

        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesional1));
        when(profesionalMapper.convertToDTO(profesional1)).thenReturn(profesionalResponseDTO1);

        // Act
        ProfesionalResponseDTO result = profesionalService.getProfesionalById(1L);

        // Asserts
        assertEquals(profesionalResponseDTO1, result);
        verify(profesionalRepository, times(1)).findById(1L);
        verify(profesionalMapper, times(1)).convertToDTO(profesional1);
    }

    @Test
    public void testGetProfesionalById_ProfesionalNoExistente() {
        when(profesionalRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Asserts
        assertThrows(ResourceNotFoundException.class, () -> profesionalService.getProfesionalById(1L));
    }

    @Test
    void getAssignedUsers_ProfesionalNoExistente() {
        Long profesionalId = 1L;

        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profesionalService.getAssignedUsers(profesionalId));
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(usuarioMapper, never()).convertToDTO(any(Usuario.class));
    }

    @Test
    void getAssignedUsers_ProfesionalExistenteConUsuarios() {
        Long profesionalId = 1L;
        Profesional profesional = new Profesional();
        profesional.setId(profesionalId);
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        profesional.setUsuarios(usuarios);

        UsuarioResponseDTO usuarioResponseDTO1 = new UsuarioResponseDTO();
        usuarioResponseDTO1.setId(usuario1.getId());
        UsuarioResponseDTO usuarioResponseDTO2 = new UsuarioResponseDTO();
        usuarioResponseDTO2.setId(usuario2.getId());

        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.of(profesional));
        when(usuarioMapper.convertToDTO(usuario1)).thenReturn(usuarioResponseDTO1);
        when(usuarioMapper.convertToDTO(usuario2)).thenReturn(usuarioResponseDTO2);

        List<UsuarioResponseDTO> result = profesionalService.getAssignedUsers(profesionalId);

        assertEquals(2, result.size());
        assertTrue(result.contains(usuarioResponseDTO1));
        assertTrue(result.contains(usuarioResponseDTO2));
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(usuarioMapper, times(1)).convertToDTO(usuario1);
        verify(usuarioMapper, times(1)).convertToDTO(usuario2);
    }

    @Test
    void getAssignedUsers_ProfesionalExistenteSinUsuarios() {
        Long profesionalId = 1L;
        Profesional profesional = new Profesional();
        profesional.setId(profesionalId);
        profesional.setUsuarios(Collections.emptyList());

        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.of(profesional));

        List<UsuarioResponseDTO> result = profesionalService.getAssignedUsers(profesionalId);

        assertTrue(result.isEmpty());
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(usuarioMapper, never()).convertToDTO(any(Usuario.class));
    }

    @Test
    void testcreateProfessional_ProfesionalExistente() {
        ProfesionalRequestDTO requestDTO = new ProfesionalRequestDTO();
        requestDTO.setNombre("Guillermo");
        requestDTO.setApellidoPaterno("Perez");
        requestDTO.setApellidoMaterno("Polo");
        requestDTO.setAnios(35);
        requestDTO.setEmail("guillermo@gmail.com");
        requestDTO.setContrasena("12345678");
        requestDTO.setTelefono("976542311");
        requestDTO.setEspecialidad("ENTRENADOR");

        when(profesionalRepository.existsByNombreEmailTelefonoProfesional(anyString(), anyString(), anyString())).thenReturn(true);

        assertThrows(ResourceDuplicateException.class, () -> profesionalService.createProfessional(requestDTO));
        verify(profesionalRepository, times(1)).existsByNombreEmailTelefonoProfesional("Guillermo", "guillermo@gmail.com", "976542311");
        verify(profesionalRepository, never()).save(any(Profesional.class));
    }

    @Test
    void testcreateProfessional_ProfesionalNuevo() {
        ProfesionalRequestDTO requestDTO = new ProfesionalRequestDTO();
        requestDTO.setNombre("Guillermo");
        requestDTO.setApellidoPaterno("Perez");
        requestDTO.setApellidoMaterno("Polo");
        requestDTO.setAnios(35);
        requestDTO.setEmail("guillermo@gmail.com");
        requestDTO.setContrasena("12345678");
        requestDTO.setTelefono("976542311");
        requestDTO.setEspecialidad("ENTRENADOR");

        Profesional profesional = new Profesional();
        profesional.setId(1L);
        profesional.setNombre(requestDTO.getNombre());
        profesional.setApellidoPaterno(requestDTO.getApellidoPaterno());
        profesional.setApellidoMaterno(requestDTO.getApellidoMaterno());
        profesional.setEmail(requestDTO.getEmail());
        profesional.setContrasena(requestDTO.getContrasena());
        profesional.setAnios(requestDTO.getAnios());
        profesional.setTelefono(requestDTO.getTelefono());
        profesional.setEspecialidad(requestDTO.getEspecialidad());
        profesional.setFechaRegistro(LocalDate.now());

        // Comportamiento simulado del repositorio y el mapper
        when(profesionalMapper.convertToEntity(requestDTO)).thenReturn(profesional);
        when(profesionalMapper.convertToDTO(profesional)).thenReturn(new ProfesionalResponseDTO(profesional.getId(), profesional.getNombre(), profesional.getApellidoPaterno(), profesional.getApellidoMaterno(),profesional.getAnios(), profesional.getEmail(), profesional.getTelefono(), profesional.getEspecialidad()));

        // Ejecución del método
        ProfesionalResponseDTO result = profesionalService.createProfessional(requestDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(profesional.getId(), result.getId());
        assertEquals(profesional.getNombre(), result.getNombre());
        assertEquals(profesional.getApellidoPaterno(), result.getApellidoPaterno());
        assertEquals(profesional.getApellidoMaterno(), result.getApellidoMaterno());
        assertEquals(profesional.getAnios(), result.getAnios());
        assertEquals(profesional.getEmail(), result.getEmail());
        assertEquals(profesional.getTelefono(), result.getTelefono());
        assertEquals(profesional.getEspecialidad(), result.getEspecialidad());

        verify(profesionalMapper, times(1)).convertToEntity(requestDTO);
        verify(profesionalRepository, times(1)).save(profesional);
        verify(profesionalMapper, times(1)).convertToDTO(profesional);
    }

    @Test
    void updateProfessional_ProfesionalExistente() {
        ProfesionalRequestDTO profesionalRequestDTO = new ProfesionalRequestDTO();
        profesionalRequestDTO.setNombre("Juan");
        profesionalRequestDTO.setApellidoPaterno("Perez");
        profesionalRequestDTO.setApellidoMaterno("Gonzales");
        profesionalRequestDTO.setEmail("juan@gmail.com");
        profesionalRequestDTO.setAnios(20);
        profesionalRequestDTO.setTelefono("987654231");
        profesionalRequestDTO.setEspecialidad("Nutrición");

        Profesional profesionalExistente = new Profesional();
        profesionalExistente.setId(1L);
        profesionalExistente.setNombre("NombreAntiguo");
        profesionalExistente.setApellidoPaterno("ApellidoPaternoAntiguo");
        profesionalExistente.setApellidoMaterno("ApellidoMaternoAntiguo");
        profesionalExistente.setEmail("emailAntiguo@gmail.com");
        profesionalExistente.setAnios(30);
        profesionalExistente.setTelefono("987654321");
        profesionalExistente.setEspecialidad("Medicina");

        Profesional profesionalActualizado = new Profesional();
        profesionalActualizado.setId(1L);
        profesionalActualizado.setNombre(profesionalRequestDTO.getNombre());
        profesionalActualizado.setApellidoPaterno(profesionalRequestDTO.getApellidoPaterno());
        profesionalActualizado.setApellidoMaterno(profesionalRequestDTO.getApellidoMaterno());
        profesionalActualizado.setEmail(profesionalRequestDTO.getEmail());
        profesionalActualizado.setAnios(profesionalRequestDTO.getAnios());
        profesionalActualizado.setTelefono(profesionalRequestDTO.getTelefono());
        profesionalActualizado.setEspecialidad(profesionalRequestDTO.getEspecialidad());
        profesionalActualizado.setFechaRegistro(LocalDate.now());

        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesionalExistente));
        when(profesionalRepository.save(profesionalExistente)).thenReturn(profesionalActualizado);

        // Mocking mapper
        ProfesionalResponseDTO profesionalResponseDTO = new ProfesionalResponseDTO();
        profesionalResponseDTO.setId(1L);
        profesionalResponseDTO.setNombre(profesionalActualizado.getNombre());
        profesionalResponseDTO.setApellidoPaterno(profesionalActualizado.getApellidoPaterno());
        profesionalResponseDTO.setApellidoMaterno(profesionalActualizado.getApellidoMaterno());
        profesionalResponseDTO.setEmail(profesionalActualizado.getEmail());
        profesionalResponseDTO.setAnios(profesionalActualizado.getAnios());
        profesionalResponseDTO.setTelefono(profesionalActualizado.getTelefono());
        profesionalResponseDTO.setEspecialidad(profesionalActualizado.getEspecialidad());
        when(profesionalMapper.convertToDTO(profesionalActualizado)).thenReturn(profesionalResponseDTO);

        // Ejecución del método
        ProfesionalResponseDTO result = profesionalService.updateProfessional(1L, profesionalRequestDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(profesionalActualizado.getId(), result.getId());
        assertEquals(profesionalRequestDTO.getNombre(), result.getNombre());
        assertEquals(profesionalRequestDTO.getApellidoPaterno(), result.getApellidoPaterno());
        assertEquals(profesionalRequestDTO.getApellidoMaterno(), result.getApellidoMaterno());
        assertEquals(profesionalRequestDTO.getEmail(), result.getEmail());
        assertEquals(profesionalRequestDTO.getAnios(), result.getAnios());
        assertEquals(profesionalRequestDTO.getTelefono(), result.getTelefono());
        assertEquals(profesionalRequestDTO.getEspecialidad(), result.getEspecialidad());
    }

    @Test
    void updateProfessional_ProfesionalNoExistente() {
        Long profesionalId = 1L;
        ProfesionalRequestDTO requestDTO = new ProfesionalRequestDTO();
        requestDTO.setNombre("Juan");
        requestDTO.setApellidoPaterno("Perez");
        requestDTO.setApellidoMaterno("Gonzales");
        requestDTO.setEmail("juan@gmail.com");
        requestDTO.setAnios(20);
        requestDTO.setTelefono("987654231");
        requestDTO.setEspecialidad("Nutrición");

        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.empty());

        // Verificación de Excepción
        assertThrows(ResourceNotFoundException.class, () -> profesionalService.updateProfessional(profesionalId, requestDTO));

        // Verificaciones
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(profesionalRepository, never()).save(any(Profesional.class));
        verify(profesionalMapper, never()).convertToDTO(any(Profesional.class));
    }

    @Test
    public void testDeleteProfesional_ProfesionalExistente() {
        // Arrange
        Long id = 1L;
        when(profesionalRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> profesionalService.deleteProfesional(id));

        // Assert
        verify(profesionalRepository, times(1)).existsById(id);
        verify(profesionalRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteProfesional_ProfesionalNoExistente() {
        // Arrange
        Long id = 1L;
        when(profesionalRepository.existsById(id)).thenReturn(false);

        // Act & Asserts
        assertThrows(ResourceNotFoundException.class, () -> profesionalService.deleteProfesional(id));
        verify(profesionalRepository, times(1)).existsById(id);
        verify(profesionalRepository, never()).deleteById(id);
    }
}
