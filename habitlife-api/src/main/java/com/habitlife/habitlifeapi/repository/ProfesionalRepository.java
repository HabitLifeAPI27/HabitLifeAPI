package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.PlanNutricional;
import com.habitlife.habitlifeapi.model.entity.Profesional;
import com.habitlife.habitlifeapi.model.entity.RutinaEjercicio;
import com.habitlife.habitlifeapi.model.entity.RutinaEstudio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProfesionalRepository {
    @Query("SELECT COUNT(p) > 0 FROM Profesional p WHERE p.nombre = :nombre AND p.email = :email AND p.telefono = :telefono")
    boolean existsByNombreEmailTelefonoProfesional(@Param("nombre") String nombre,
                                      @Param("email") String email,
                                      @Param("telefono") String telefono);

    List<Profesional> findByNombre(String nombre);

    Profesional findByEmail(String email);

    List<Profesional> findByAnios(int anios);

    List<Profesional> findByEspecialidad(String especialidad);

    List<Profesional> findByFechaRegistro(LocalDate fechaRegistro);

    List<Profesional> findByPlanesNutricionales(PlanNutricional planNutricional);

    List<Profesional> findByRutinasEjercicios(RutinaEjercicio rutinaEjercicio);

    List<Profesional> findByRutinasEstudios(RutinaEstudio rutinaEstudio);
}
