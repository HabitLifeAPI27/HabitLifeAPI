package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.RutinaEjercicio;
import com.habitlife.habitlifeapi.model.entity.RutinaEstudio;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RutinaEstudioRepository {
    List<RutinaEstudio> findByUsuarioId(Long usuarioId);

    List<RutinaEstudio> findByNombre(String nombre);

    List<RutinaEstudio> findByDescripcionContaining(String keyword);

    List<RutinaEstudio> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<RutinaEstudio> findByProfesionalId(Long profesionalId);

    List<RutinaEstudio> findByHoras(int horas);

    List<RutinaEstudio> findByCurso(String curso);
}
