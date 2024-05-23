package com.habitlife.habitlifeapi.repository;

import com.habitlife.habitlifeapi.model.entity.Objetivo;
import com.habitlife.habitlifeapi.model.entity.Pago;
import com.habitlife.habitlifeapi.model.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PagoRepository {
    List<Pago> findByUsuarioId(Long usuarioId);

    List<Pago> findByEstadoPago(Status estadoPago);

    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :startDate AND :endDate")
    List<Pago> findPagosBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Pago p WHERE p.metodoPago = :metodoPago")
    List<Pago> findByMetodoPago(@Param("metodoPago") String metodoPago);
}
