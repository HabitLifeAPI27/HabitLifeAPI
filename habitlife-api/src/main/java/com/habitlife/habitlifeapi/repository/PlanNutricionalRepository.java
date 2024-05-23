package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.Objetivo;
import com.habitlife.habitlifeapi.model.entity.PlanNutricional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlanNutricionalRepository {
    List<PlanNutricional> findByUsuarioId(Long usuarioId);

    List<PlanNutricional> findByProfesionalId(Long profesionalId);

    @Query("SELECT COUNT(p) > 0 FROM PlanNutricional p WHERE p.nombre = :nombre")
    boolean existsByNombrePlan(@Param("nombre") String nombre);

    List<PlanNutricional> findByNombre(String nombre);

    List<PlanNutricional> findByDescripcionContaining(String keyword);

    List<PlanNutricional> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<PlanNutricional> findByAlimentos(String alimentos);
}
