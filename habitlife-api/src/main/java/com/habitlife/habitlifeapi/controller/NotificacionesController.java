package com.habitlife.habitlifeapi.controller;

import com.habitlife.habitlifeapi.model.dto.NotificacionesResponseDTO;
import com.habitlife.habitlifeapi.service.NotificacionesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@AllArgsConstructor
public class NotificacionesController {
    private final NotificacionesService notificacionesService;

    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarNotificacion(
            @RequestParam Long usuarioId,
            @RequestParam String mensaje) {
        notificacionesService.enviarNotificacion(usuarioId, mensaje);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/no-leidas/{usuarioId}")
    public ResponseEntity<List<NotificacionesResponseDTO>> obtenerNotificacionesNoLeidas(@PathVariable Long usuarioId) {
        List<NotificacionesResponseDTO> notificaciones = notificacionesService.obtenerNotificacionesNoLeidas(usuarioId);
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    @PostMapping("/marcar-leida/{notificacionId}")
    public ResponseEntity<Void> marcarNotificacionComoLeida(@PathVariable Long notificacionId) {
        notificacionesService.marcarNotificacionComoLeida(notificacionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Métodos para probar las tareas programadas
    @GetMapping("/verificar-objetivos")
    public ResponseEntity<Void> verificarObjetivos() {
        notificacionesService.verificarObjetivos();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verificar-planes-nutricionales")
    public ResponseEntity<Void> verificarPlanesNutricionales() {
        notificacionesService.verificarPlanesNutricionales();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verificar-rutinas-ejercicios")
    public ResponseEntity<Void> verificarRutinasEjercicios() {
        notificacionesService.verificarRutinasEjercicios();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verificar-rutinas-estudios")
    public ResponseEntity<Void> verificarRutinasEstudios() {
        notificacionesService.verificarRutinasEstudios();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
