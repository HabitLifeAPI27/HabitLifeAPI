package com.habitlife.habitlifeapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanNutricionalRequestDTO {
    @NotBlank(message = "El nombre del plan nutricional no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @NotBlank(message = "La fecha de inicio no puede estar vacia")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;

    @NotBlank(message = "La fecha de inicio no puede estar vacia")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;

    @NotBlank(message = "Los alimentos no pueden estar vacios")
    @Size(min=5, max=100, message = "Los alimentos deben tener entre 5 a 100 caracteres")
    private String alimentos;
}
