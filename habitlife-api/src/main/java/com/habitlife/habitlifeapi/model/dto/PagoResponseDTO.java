package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoResponseDTO {
    private Long id;
    private Long usuarioId;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String estadoPago;
    private String metodoPago;
}
