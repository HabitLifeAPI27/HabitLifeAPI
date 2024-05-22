package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutinaEjercicioResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int series;
    private int repeticiones;
}
