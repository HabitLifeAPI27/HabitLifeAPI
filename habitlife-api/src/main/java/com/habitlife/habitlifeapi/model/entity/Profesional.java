package com.habitlife.habitlifeapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profesionales")
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false)
    private String apelldioMaterno;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "anios", nullable = false)
    private int anios;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDate fechaActualizacion;

    @Column(name = "telefono_id", nullable = false)
    private String telefono;

    @Column(name = "especialidad_id", nullable = false)
    private String especialidad;

    @ManyToOne
    @JoinColumn(name = "rutina_ejercicio_id", nullable = false)
    private RutinaEjercicio rutinaEjercicio;

    @ManyToOne
    @JoinColumn(name = "rutina_ejercicio_id", nullable = false)
    private RutinaEstudio rutinaEstudio;

    @ManyToOne
    @JoinColumn(name = "plan_nutricional_id", nullable = false)
    private PlanNutricional planNutricional;
}
