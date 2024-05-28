package com.habitlife.habitlifeapi.service;

import com.habitlife.habitlifeapi.exception.ResourceNotFoundException;
import com.habitlife.habitlifeapi.mapper.NotificacionesMapper;
import com.habitlife.habitlifeapi.model.dto.NotificacionesResponseDTO;
import com.habitlife.habitlifeapi.model.entity.*;
import com.habitlife.habitlifeapi.model.enums.Status;
import com.habitlife.habitlifeapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionesService {
    private final NotificacionesRepository notificacionesRepository;
    private final UsuarioRepository usuarioRepository;
    private final RutinaEjercicioRepository rutinaEjercicioRepository;
    private final RutinaEstudioRepository rutinaEstudioRepository;
    private final PlanNutricionalRepository planNutricionalRepository;
    private final ObjetivoRepository objetivoRepository;
    private final NotificacionesMapper notificacionesMapper;

    public void enviarNotificacion(Long usuarioId, String mensaje) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + usuarioId));

        Notificaciones notificacion = new Notificaciones();
        notificacion.setUsuario(usuario);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setLeida(false);

        notificacionesRepository.save(notificacion);
    }

    @Transactional(readOnly = true)
    public List<NotificacionesResponseDTO> obtenerNotificacionesNoLeidas(Long usuarioId) {
        List<Notificaciones> notificaciones = notificacionesRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
        return notificacionesMapper.convertToListDTO(notificaciones);
    }

    @Transactional
    public void marcarNotificacionComoLeida(Long notificacionId) {
        Notificaciones notificacion = notificacionesRepository.findById(notificacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con el ID: " + notificacionId));

        notificacion.setLeida(true);
        notificacionesRepository.save(notificacion);
    }

    @Scheduled(cron = "0 0 21 * * ?") // Corre todos los días a las 9 PM
    public void verificarObjetivos() {
        LocalDate hoy = LocalDate.now();
        List<Objetivo> objetivos = objetivoRepository.findAllByFechaFinBeforeAndEstadoObjetivo(hoy, Status.PENDIENTE);
        for (Objetivo objetivo : objetivos) {
            enviarNotificacion(objetivo.getUsuario().getId(), "Objetivo No Cumplido: No has cumplido el objetivo: " + objetivo.getNombre());
        }
    }

    @Scheduled(cron = "0 0 21 * * ?") // Corre todos los días a las 9 PM
    public void verificarPlanesNutricionales() {
        LocalDate hoy = LocalDate.now();
        List<PlanNutricional> planes = planNutricionalRepository.findAllByFechaFinBefore(hoy);
        for (PlanNutricional plan : planes) {
            enviarNotificacion(plan.getUsuario().getId(), "Plan Nutricional No Cumplido: No has cumplido el plan nutricional: " + plan.getNombre());
        }
    }

    @Scheduled(cron = "0 0 21 * * ?") // Corre todos los días a las 9 PM
    public void verificarRutinasEjercicios() {
        LocalDate hoy = LocalDate.now();
        List<RutinaEjercicio> rutinas = rutinaEjercicioRepository.findAllByFechaFinBefore(hoy);
        for (RutinaEjercicio rutinaEjercicio : rutinas) {
            enviarNotificacion(rutinaEjercicio.getUsuario().getId(), "Rutina No Cumplida: No has cumplido la rutina: " + rutinaEjercicio.getNombre());
        }
    }

    @Scheduled(cron = "0 0 21 * * ?") // Corre todos los días a las 9 PM
    public void verificarRutinasEstudios() {
        LocalDate hoy = LocalDate.now();
        List<RutinaEstudio> rutinas = rutinaEstudioRepository.findAllByFechaFinBefore(hoy);
        for (RutinaEstudio rutinaEstudio : rutinas) {
            enviarNotificacion(rutinaEstudio.getUsuario().getId(), "Rutina No Cumplida: No has cumplido la rutina: " + rutinaEstudio.getNombre());
        }
    }
}
