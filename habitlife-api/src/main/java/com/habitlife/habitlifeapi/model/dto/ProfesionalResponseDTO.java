package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionalResponseDTO {
    private long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String anios;
    private String email;
    private String especialidad;
    private LocalDate fechaRegistro;
    private LocalDate fechaActualizacion;

}
