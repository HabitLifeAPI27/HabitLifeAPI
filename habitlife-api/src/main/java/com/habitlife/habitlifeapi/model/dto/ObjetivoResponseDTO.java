package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjetivoResponseDTO {
    private long id;
    private UsuarioResponseDTO usuarioobjetivo;
    private String nombre;
    private LocalDate fechaInicio;
    private Local fechaFin;
}
