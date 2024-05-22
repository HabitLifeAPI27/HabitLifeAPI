package com.habitlife.habitlifeapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @NotBlank(message = "El monto del pago no puede estar vacio")
    @Size(min=1, max=3, message = "El monto del pago debe tener entre 1 y 3 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El monto del pago debe contener solo digitos")
    private Double monto;
}
