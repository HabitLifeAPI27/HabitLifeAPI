package com.habitlife.habitlifeapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "anios", nullable = false)
    private int anios;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDate fechaActualizacion;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "premium", nullable = false)
    private boolean premium;

    @Column(name = "fecha_premium", nullable = false)
    private LocalDate fechaPremium;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Objetivo> objetivos;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlanNutricional> planesNutricionales;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RutinaEjercicio> rutinasEjercicios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RutinaEstudio> rutinasEstudios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;  // Lista de pagos realizados por el usuario
}
