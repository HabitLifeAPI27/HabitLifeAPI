package com.habitlife.habitlifeapi.service;

import com.habitlife.habitlifeapi.exception.ResourceDuplicateException;
import com.habitlife.habitlifeapi.exception.ResourceNotFoundException;
import com.habitlife.habitlifeapi.mapper.UsuarioMapper;
import com.habitlife.habitlifeapi.model.dto.UsuarioRequestDTO;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private ProfesionalRepository profesionalRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testgetAllUsers(){
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        UsuarioResponseDTO usuarioResponseDTO1 = new UsuarioResponseDTO();
        usuarioResponseDTO1.setId(usuario1.getId());
        UsuarioResponseDTO usuarioResponseDTO2 = new UsuarioResponseDTO();
        usuarioResponseDTO2.setId(usuario2.getId());

        List<UsuarioResponseDTO> expectedResponse = Arrays.asList(usuarioResponseDTO1, usuarioResponseDTO2);
        when(usuarioMapper.convertToListDTO(usuarios)).thenReturn(expectedResponse);

        //Act
        List<UsuarioResponseDTO> result =usuarioService.getAllUsers();

        //Assert
        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        // Verify that repository and mapper methods were called
        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioMapper, times(1)).convertToListDTO(usuarios);
    }

    @Test
    public void testgetUserById_UsuarioExistente(){
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        UsuarioResponseDTO usuarioResponseDTO1 = new UsuarioResponseDTO();
        usuarioResponseDTO1.setId(usuario1.getId());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioMapper.convertToDTO(usuario1)).thenReturn(usuarioResponseDTO1);

        // Act
        UsuarioResponseDTO result = usuarioService.getUserById(1L);

        // Asserts
        assertEquals(usuarioResponseDTO1, result);
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioMapper, times(1)).convertToDTO(usuario1);
    }

    @Test
    public void testgetUserById_UsuarioNoExistente(){
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Asserts
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.getUserById(1L));
    }

    @Test
    public void testCreateUser_UsuarioExistente(){
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Gonzales");
        usuarioRequestDTO.setEmail("juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(20);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Tomás Marsano 4527");
        usuarioRequestDTO.setTelefono("987654231");

        when(usuarioRepository.existsByNombreEmailTelefonoUsuario(anyString(), anyString(), anyString())).thenReturn(true);

        //Asserts
        assertThrows(ResourceDuplicateException.class, () -> usuarioService.createAccount(usuarioRequestDTO));
        verify(usuarioRepository, times(1)).existsByNombreEmailTelefonoUsuario("Juan", "juan@gmail.com", "987654231");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testcreateAccount_UsuarioNuevo() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Gonzales");
        usuarioRequestDTO.setEmail("juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(20);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Tomás Marsano 4527");
        usuarioRequestDTO.setTelefono("987654231");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setApellidoPaterno(usuarioRequestDTO.getApellidoPaterno());
        usuario.setApellidoMaterno(usuarioRequestDTO.getApellidoMaterno());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setContrasena(usuarioRequestDTO.getContrasena());
        usuario.setAnios(usuarioRequestDTO.getAnios());
        usuario.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuario.setPremium(false);
        usuario.setDireccion(usuarioRequestDTO.getDireccion());
        usuario.setTelefono(usuarioRequestDTO.getTelefono());
        usuario.setFechaRegistro(LocalDate.now());

        // Comportamiento simulado del repositorio y el mapper
        when(usuarioMapper.convertToEntity(usuarioRequestDTO)).thenReturn(usuario);
        when(usuarioMapper.convertToDTO(usuario)).thenReturn(new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno(), usuario.getEmail(), usuario.getAnios(), usuario.getObjetivos(), usuario.getDireccion(), usuario.getTelefono(), usuario.getProfesionales()));

        // Ejecución del método
        UsuarioResponseDTO result = usuarioService.createAccount(usuarioRequestDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals(usuario.getNombre(), result.getNombre());
        assertEquals(usuario.getApellidoPaterno(), result.getApellidoPaterno());
        assertEquals(usuario.getApellidoMaterno(), result.getApellidoMaterno());
        assertEquals(usuario.getAnios(), result.getAnios());
        assertEquals(usuario.getObjetivos(), result.getObjetivos());
        assertEquals(usuario.getDireccion(), result.getDireccion());
        assertEquals(usuario.getTelefono(), result.getTelefono());

        verify(usuarioMapper, times(1)).convertToEntity(usuarioRequestDTO);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(usuarioMapper, times(1)).convertToDTO(usuario);
    }

    @Test
    void updateAccount_UsuarioExistente() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Gonzales");
        usuarioRequestDTO.setEmail("juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(20);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Tomás Marsano 4527");
        usuarioRequestDTO.setTelefono("987654231");

        Usuario usuarioexistente = new Usuario();
        usuarioexistente.setId(1L);
        usuarioexistente.setNombre("Nombre2");
        usuarioexistente.setApellidoPaterno("Apellido2");
        usuarioexistente.setApellidoMaterno("Apellido3");
        usuarioexistente.setEmail("juan123@gmail.com");
        usuarioexistente.setContrasena("87654321");
        usuarioexistente.setAnios(30);
        usuarioexistente.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioexistente.setDireccion("Av. Tomás Marsano 3213");
        usuarioexistente.setTelefono("987654321");

        Usuario usuarioactualizado = new Usuario();
        usuarioactualizado.setId(1L);
        usuarioactualizado.setNombre(usuarioRequestDTO.getNombre());
        usuarioactualizado.setApellidoPaterno(usuarioRequestDTO.getApellidoPaterno());
        usuarioactualizado.setApellidoMaterno(usuarioRequestDTO.getApellidoMaterno());
        usuarioactualizado.setEmail(usuarioRequestDTO.getEmail());
        usuarioactualizado.setContrasena(usuarioRequestDTO.getContrasena());
        usuarioactualizado.setAnios(usuarioRequestDTO.getAnios());
        usuarioactualizado.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioactualizado.setPremium(false);
        usuarioactualizado.setDireccion(usuarioRequestDTO.getDireccion());
        usuarioactualizado.setTelefono(usuarioRequestDTO.getTelefono());
        usuarioactualizado.setFechaRegistro(LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioexistente));
        when(usuarioRepository.save(usuarioexistente)).thenReturn(usuarioactualizado);

        //Mocking mapper
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre(usuarioactualizado.getNombre());
        usuarioResponseDTO.setApellidoPaterno(usuarioactualizado.getApellidoPaterno());
        usuarioResponseDTO.setApellidoMaterno(usuarioactualizado.getApellidoMaterno());
        usuarioResponseDTO.setEmail(usuarioactualizado.getEmail());
        usuarioResponseDTO.setAnios(usuarioactualizado.getAnios());
        usuarioResponseDTO.setObjetivos(usuarioactualizado.getObjetivos());
        usuarioResponseDTO.setDireccion(usuarioactualizado.getDireccion());
        usuarioResponseDTO.setTelefono(usuarioactualizado.getTelefono());
        when(usuarioMapper.convertToDTO(usuarioactualizado)).thenReturn(usuarioResponseDTO);

        // Ejecución del método
        UsuarioResponseDTO result = usuarioService.updateAccount(1L, usuarioRequestDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(usuarioactualizado.getId(), result.getId());
        assertEquals(usuarioRequestDTO.getNombre(), result.getNombre());
        assertEquals(usuarioRequestDTO.getApellidoPaterno(), result.getApellidoPaterno());
        assertEquals(usuarioRequestDTO.getApellidoMaterno(), result.getApellidoMaterno());
        assertEquals(usuarioRequestDTO.getAnios(), result.getAnios());
        assertEquals(usuarioRequestDTO.getObjetivos(), result.getObjetivos());
        assertEquals(usuarioRequestDTO.getDireccion(), result.getDireccion());
        assertEquals(usuarioRequestDTO.getTelefono(), result.getTelefono());
    }

    @Test
    void updateAccount_UsuarioNoExistente(){
        Long id = 1L;
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellidoPaterno("Perez");
        usuarioRequestDTO.setApellidoMaterno("Gonzales");
        usuarioRequestDTO.setEmail("juan@gmail.com");
        usuarioRequestDTO.setContrasena("12345678");
        usuarioRequestDTO.setAnios(20);
        usuarioRequestDTO.setObjetivos(usuarioRequestDTO.getObjetivos());
        usuarioRequestDTO.setDireccion("Av. Tomás Marsano 4527");
        usuarioRequestDTO.setTelefono("987654231");

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Asserts
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.updateAccount(id, usuarioRequestDTO));
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void testDeleteUser_UsuarioExistente() {
        // Arrange
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> usuarioService.deleteUser(id));

        // Assert
        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteUser_UsuarioNoExistente() {
        // Arrange
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        // Act & Asserts
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.deleteUser(id));
        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, never()).deleteById(id);
    }

    @Test
    void asignarProfesional_UsuarioNoExistente() {
        Long usuarioId = 1L;
        Long profesionalId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.asignarProfesional(usuarioId, profesionalId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(profesionalRepository, never()).findById(profesionalId);
    }

    @Test
    void asignarProfesional_ProfesionalNoExistente() {
        Long usuarioId = 1L;
        Long profesionalId = 1L;
        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.asignarProfesional(usuarioId, profesionalId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(profesionalRepository, times(1)).findById(profesionalId);
    }

    @Test
    void asignarProfesional_ProfesionalYaAsignado() {
        Long usuarioId = 1L;
        Long profesionalId = 1L;
        Usuario usuario = new Usuario();
        Profesional profesional = new Profesional();
        usuario.getProfesionales().add(profesional);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.of(profesional));

        assertThrows(ResourceDuplicateException.class, () -> usuarioService.asignarProfesional(usuarioId, profesionalId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(usuarioRepository, never()).save(usuario);
    }

    @Test
    void asignarProfesional_AsignarNuevoProfesional() {
        Long usuarioId = 1L;
        Long profesionalId = 1L;
        Usuario usuario = new Usuario();
        Profesional profesional = new Profesional();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(profesionalRepository.findById(profesionalId)).thenReturn(Optional.of(profesional));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.convertToDTO(any(Usuario.class))).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.asignarProfesional(usuarioId, profesionalId);

        assertEquals(responseDTO, result);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(profesionalRepository, times(1)).findById(profesionalId);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(usuarioMapper, times(1)).convertToDTO(usuario);
    }

}
