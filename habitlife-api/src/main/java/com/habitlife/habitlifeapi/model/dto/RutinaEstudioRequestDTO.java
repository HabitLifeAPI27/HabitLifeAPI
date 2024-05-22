package com.habitlife.habitlifeapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutinaEstudioRequestDTO {
    @NotBlank(message = "El nombre de la rutina no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @NotBlank(message = "La fecha inicio no puede estar vacia")
    @DateTimeFormat
    private LocalDate fechaInicio;

    @NotBlank(message = "La fecha final no puede estar vacia")
    @DateTimeFormat
    private LocalDate fechaFin;

    @NotBlank(message = "El numero de horas no puede estar vacio")
    @Pattern(regexp = "[0-9]+", message = "Las horas debe contener solo digitos")
    private int horas;

    @NotBlank(message = "El nombre del curso no puede estar vacio")
    private String curso;
}
