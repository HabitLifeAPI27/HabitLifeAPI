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

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    @NotBlank(message = "La especialidad no puede estar vacio")
    private String especialidad;
}
