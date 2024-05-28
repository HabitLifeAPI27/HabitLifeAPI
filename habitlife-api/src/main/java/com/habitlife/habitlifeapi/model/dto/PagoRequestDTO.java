package com.habitlife.habitlifeapi.model.dto;

import com.habitlife.habitlifeapi.model.enums.MetodosPago;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {
    @NotNull(message = "El número de pago no puede ser nulo")
    private Long numeroPago;

    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Long usuarioId;

    @NotNull(message = "El monto del pago no puede estar vacio")
    @Min(value = 0, message = "El monto del pago no puede ser negativo")
    private BigDecimal monto;

    @NotNull(message = "El método de pago no puede estar vacío")
    private MetodosPago metodoPago;
}
