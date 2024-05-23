package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.RutinaEjercicio;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RutinaEjercicioRepository {
    List<RutinaEjercicio> findByUsuarioId(Long usuarioId);

    List<RutinaEjercicio> findByNombre(String nombre);

    List<RutinaEjercicio> findByDescripcionContaining(String keyword);

    List<RutinaEjercicio> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<RutinaEjercicio> findByProfesionalId(Long profesionalId);

    // Buscar rutinas de ejercicio por número de repeticiones
    @Query("SELECT re FROM RutinaEjercicio re WHERE re.repeticiones = :repeticiones")
    List<RutinaEjercicio> findByRepeticiones(int repeticiones);

    // Buscar rutinas de ejercicio por número de series
    @Query("SELECT re FROM RutinaEjercicio re WHERE re.series = :series")
    List<RutinaEjercicio> findBySeries(int series);
}
