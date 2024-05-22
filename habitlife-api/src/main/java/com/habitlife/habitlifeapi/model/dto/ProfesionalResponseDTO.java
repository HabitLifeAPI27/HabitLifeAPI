package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionalResponseDTO {
    private long id;
    private String nombre;
    private String apellidoPaterno;
    private String email;
    private String especialidad;

}
