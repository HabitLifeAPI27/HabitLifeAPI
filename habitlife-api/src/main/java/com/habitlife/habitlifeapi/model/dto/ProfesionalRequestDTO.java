package com.habitlife.habitlifeapi.model.dto;

import com.habitlife.habitlifeapi.model.entity.PlanNutricional;
import com.habitlife.habitlifeapi.model.entity.RutinaEjercicio;
import com.habitlife.habitlifeapi.model.entity.RutinaEstudio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionalRequestDTO {
    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido paterno del usuario no puede estar vacio")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno del usuario no puede estar vacio")
    private String apellidoMaterno;

    @NotBlank(message = "El correo del usuario no puede estar vacio")
    @Email
    private String email;

    @NotBlank(message = "La especialidad no puede estar vacio")
    private String especialidad;

    private PlanNutricional planNutricional;
    private RutinaEjercicio rutinaEjercicio;
    private RutinaEstudio rutinaEstudio;
}
