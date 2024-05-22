package com.habitlife.habitlifeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private long id;
    private String nombre;
    private String apellidoPaterno;
    private String email;
    private boolean premium;
    private String objetivo;
    private BigDecimal saldo;
}
