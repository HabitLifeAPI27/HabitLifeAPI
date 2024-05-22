package com.habitlife.habitlifeapi.mapper;

import com.habitlife.habitlifeapi.model.dto.RutinaEjercicioRequestDTO;
import com.habitlife.habitlifeapi.model.dto.RutinaEjercicioResponseDTO;
import com.habitlife.habitlifeapi.model.entity.RutinaEjercicio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RutinaEjercicioMapper {
    private final ModelMapper modelMapper;

    public RutinaEjercicio convertToEntity(RutinaEjercicioRequestDTO RutinaEjercicioRequestDTO){
        return modelMapper.map(RutinaEjercicioRequestDTO, RutinaEjercicio.class);
    }

    public RutinaEjercicioResponseDTO convertToDTO(RutinaEjercicio rutinaejercicio){
        return modelMapper.map(rutinaejercicio, RutinaEjercicioResponseDTO.class);
    }

    public List<RutinaEjercicioResponseDTO> convertToListDTO(List<RutinaEjercicio> rutinaejercicios){

        return rutinaejercicios.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
