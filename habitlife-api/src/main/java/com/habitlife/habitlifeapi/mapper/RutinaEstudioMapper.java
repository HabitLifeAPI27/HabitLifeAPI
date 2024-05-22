package com.habitlife.habitlifeapi.mapper;

import com.habitlife.habitlifeapi.model.dto.RutinaEstudioRequestDTO;
import com.habitlife.habitlifeapi.model.dto.RutinaEstudioResponseDTO;
import com.habitlife.habitlifeapi.model.entity.RutinaEstudio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RutinaEstudioMapper {
    private final ModelMapper modelMapper;

    public RutinaEstudio convertToEntity(RutinaEstudioRequestDTO RutinaEstudioRequestDTO){
        return modelMapper.map(RutinaEstudioRequestDTO, RutinaEstudio.class);
    }

    public RutinaEstudioResponseDTO convertToDTO(RutinaEstudio rutinaestudio){
        return modelMapper.map(rutinaestudio, RutinaEstudioResponseDTO.class);
    }

    public List<RutinaEstudioResponseDTO> convertToListDTO(List<RutinaEstudio> rutinaestudios){

        return rutinaestudios.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
