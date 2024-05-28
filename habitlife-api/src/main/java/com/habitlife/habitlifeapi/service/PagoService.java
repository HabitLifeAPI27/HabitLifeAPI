package com.habitlife.habitlifeapi.service;

import com.habitlife.habitlifeapi.exception.BadRequestException;
import com.habitlife.habitlifeapi.exception.ResourceNotFoundException;
import com.habitlife.habitlifeapi.mapper.PagoMapper;
import com.habitlife.habitlifeapi.model.dto.PagoReportDTO;
import com.habitlife.habitlifeapi.model.dto.PagoRequestDTO;
import com.habitlife.habitlifeapi.model.dto.PagoResponseDTO;
import com.habitlife.habitlifeapi.model.entity.Pago;
import com.habitlife.habitlifeapi.model.entity.Usuario;
import com.habitlife.habitlifeapi.model.enums.Status;
import com.habitlife.habitlifeapi.repository.PagoRepository;
import com.habitlife.habitlifeapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoMapper pagoMapper;
    private final PaymentServiceExternal paymentServiceExternal;
    private final NotificacionesService notificacionesService;

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> getAllPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagoMapper.convertToListDTO(pagos);
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO getPagoById(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con el ID: " + id));
        return pagoMapper.convertToDTO(pago);
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> getPagosByUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + usuarioId));

        List<Pago> pagos = pagoRepository.findByUsuarioId(usuarioId);
        return pagoMapper.convertToListDTO(pagos);
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> getPagosBetweenFechasPagos(LocalDate fechaPago1, LocalDate fechaPago2) {
        List<Pago> pagos = pagoRepository.findPagosBetweenDates(fechaPago1, fechaPago2);
        return pagoMapper.convertToListDTO(pagos);
    }

    @Transactional
    public PagoResponseDTO crearPago(PagoRequestDTO pagoRequestDTO) {
        // Buscar el usuario por su ID
        Usuario usuario = usuarioRepository.findById(pagoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + pagoRequestDTO.getUsuarioId()));


        boolean paymentProcessed = paymentServiceExternal.processPayment(pagoRequestDTO);

        if(paymentProcessed){

            BigDecimal montoPago = pagoRequestDTO.getMonto();

            // Crear el objeto Pago y guardarlo en la base de datos
            Pago pago = pagoMapper.convertToEntity(pagoRequestDTO);
            pago.setUsuario(usuario);
            pago.setMonto(montoPago);
            pago.setFechaPago(LocalDate.now());
            pago.setEstadoPago(Status.COMPLETADO);
            pago.setMetodoPago(pagoRequestDTO.getMetodoPago());
            usuario.setPremium(true);

            pagoRepository.save(pago);
            notificacionesService.enviarNotificacion(usuario.getId(), "Membresía Premium: Tu pago ha sido procesado exitosamente y ahora tienes una membresía premium.");
            // Devolver el DTO de respuesta del pago
            return pagoMapper.convertToDTO(pago);
        }
        else{
            notificacionesService.enviarNotificacion(usuario.getId(), "Pago Fallido: Tu pago no pudo ser procesado. Por favor, intenta nuevamente.");
            throw new BadRequestException("Saldo insuficiente para generar el pago del pedido:" + pagoRequestDTO.getNumeroPago());
        }

    }

    @Transactional
    public List<PagoReportDTO> generarReporteDePagos(String startdate, String enddate, String usuarioid) {

        LocalDate startDate =LocalDate.parse(startdate);
        LocalDate endDate =LocalDate.parse(enddate);

        List<Object[]> pagoCount = pagoRepository.
                getPagosCountByDateRangeAndId(startDate, endDate, usuarioid);
        return pagoCount.stream()
                .map(pagoMapper::convertPagoReportDTO)
                .toList();
        
    }


}
