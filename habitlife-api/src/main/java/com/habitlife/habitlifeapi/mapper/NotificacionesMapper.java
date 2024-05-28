package com.habitlife.habitlifeapi.mapper;

import com.habitlife.habitlifeapi.model.dto.NotificacionesRequestDTO;
import com.habitlife.habitlifeapi.model.dto.NotificacionesResponseDTO;
import com.habitlife.habitlifeapi.model.entity.Notificaciones;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificacionesMapper {
    private final ModelMapper modelMapper;

    public Notificaciones convertToEntity(NotificacionesRequestDTO notificacionRequestDTO) {
        return modelMapper.map(notificacionRequestDTO, Notificaciones.class);
    }

    public NotificacionesResponseDTO convertToDTO(Notificaciones notificacion) {
        return modelMapper.map(notificacion, NotificacionesResponseDTO.class);
    }

    public List<NotificacionesResponseDTO> convertToListDTO(List<Notificaciones> notificaciones){

        return notificaciones.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
