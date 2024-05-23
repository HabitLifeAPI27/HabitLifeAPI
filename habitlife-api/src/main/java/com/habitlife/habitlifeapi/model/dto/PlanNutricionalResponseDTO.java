package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanNutricionalResponseDTO {
    private Long id;
    private Long usuarioId;
    private String nombreUsuario; // Nombre del usuario para facilitar la lectura
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String alimentos;
    private Long profesionalId;
    private String nombreProfesional;
}
