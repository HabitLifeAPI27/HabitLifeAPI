package com.habitlife.habitlifeapi.model.dto;

import com.habitlife.habitlifeapi.model.entity.Objetivo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido paterno del usuario no puede estar vacio")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno del usuario no puede estar vacio")
    private String apellidoMaterno;

    @NotBlank(message = "El correo del usuario no puede estar vacio")
    @Email
    private String email;

    @NotBlank(message = "La edad del usuario no puede estar vacia")
    @Size(min=1, max=2, message = "La edad del usuario debe tener entre 1 y 2 caracteres")
    @Pattern(regexp = "[0-9]+", message = "La edad debe contener solo digitos")
    private int anios;

    @NotBlank(message = "El objetivo no puede estar vacio")
    private Objetivo objetivo;

    @Pattern(regexp = "[0-9]+", message = "El plan debe contener solo digitos")
    private BigDecimal saldo;

    @NotBlank(message = "El plan del usuario no puede estar vacio")
    @Pattern(regexp = "[0-9]+", message = "El plan debe contener solo digitos")
    private boolean premium;
}
