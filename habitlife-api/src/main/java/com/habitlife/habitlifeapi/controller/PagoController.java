package com.habitlife.habitlifeapi.controller;

import com.habitlife.habitlifeapi.model.dto.PagoReportDTO;
import com.habitlife.habitlifeapi.model.dto.PagoRequestDTO;
import com.habitlife.habitlifeapi.model.dto.PagoResponseDTO;
import com.habitlife.habitlifeapi.service.PagoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagos")
@AllArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> getAllPagos() {
        List<PagoResponseDTO> pagos = pagoService.getAllPagos();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> getPagoById(@PathVariable Long id) {
        PagoResponseDTO pago = pagoService.getPagoById(id);
        return new ResponseEntity<>(pago, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<PagoResponseDTO>> getPagosByUsuarioId(@PathVariable Long usuarioId) {
        List<PagoResponseDTO> pagos = pagoService.getPagosByUsuarioId(usuarioId);
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @GetMapping("/{fechaPago1}/{fechaPago2}")
    public ResponseEntity<List<PagoResponseDTO>> getPagosBetweenFechas(@PathVariable LocalDate fechaPago1,
                                                                       @PathVariable LocalDate fechaPago2) {
        List<PagoResponseDTO> pagos = pagoService.getPagosBetweenFechasPagos(fechaPago1, fechaPago2);
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@RequestBody @Validated PagoRequestDTO pagoRequestDTO) {
        PagoResponseDTO pago = pagoService.crearPago(pagoRequestDTO);
        return new ResponseEntity<>(pago, HttpStatus.CREATED);
    }

    @GetMapping("/reporte/{startdate}/{enddate}/usuarios/{usuarioid}")
    public ResponseEntity<List<PagoReportDTO>> generarReporteDePagos(@PathVariable String startdate,
                                                                     @PathVariable String enddate,
                                                                     @PathVariable String usuarioid) {
        List<PagoReportDTO> reporte = pagoService.generarReporteDePagos(startdate, enddate, usuarioid);
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }
}
