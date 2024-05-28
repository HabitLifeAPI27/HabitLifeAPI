package com.habitlife.habitlifeapi.service;

import com.habitlife.habitlifeapi.model.dto.PagoRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceExternal {

    private static  BigDecimal AVAILABLE_BALANCE = BigDecimal.valueOf(5000.0);

    public boolean processPayment(PagoRequestDTO pagoRequestDTO) {
        BigDecimal paymentAmount = pagoRequestDTO.getMonto();
        boolean paymentProcessed = false;

        if (AVAILABLE_BALANCE.compareTo(paymentAmount) >= 0) {
            AVAILABLE_BALANCE = AVAILABLE_BALANCE.subtract(paymentAmount);
            paymentProcessed = true;
        }

        return paymentProcessed;
    }

}