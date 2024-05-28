package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
    // Buscar notificaciones por ID de usuario
    List<Notificaciones> findByUsuarioId(Long usuarioId);

    List<Notificaciones> findByUsuarioIdAndLeidaFalse(Long usuarioId);
}
