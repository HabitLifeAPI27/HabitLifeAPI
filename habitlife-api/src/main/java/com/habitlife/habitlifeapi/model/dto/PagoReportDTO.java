package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoReportDTO {
    private Long id;
    private String usuarioNombre;
    private String usuarioEmail;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String estadoPago;
    private String metodoPago;
}
