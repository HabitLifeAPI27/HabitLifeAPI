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
public class RutinaEjercicioRequestDTO {
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

    @NotBlank(message = "El numero de series no puede estar vacio")
    @Pattern(regexp = "[0-9]+", message = "La edad debe contener solo digitos")
    private int series;

    @NotBlank(message = "El numero de repeticiones no puede estar vacio")
    @Pattern(regexp = "[0-9]+", message = "La edad debe contener solo digitos")
    private int repeticiones;
}
