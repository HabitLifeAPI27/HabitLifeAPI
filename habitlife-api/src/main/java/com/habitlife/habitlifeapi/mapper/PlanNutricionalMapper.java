package com.habitlife.habitlifeapi.mapper;

import com.habitlife.habitlifeapi.model.dto.PlanNutricionalRequestDTO;
import com.habitlife.habitlifeapi.model.dto.PlanNutricionalResponseDTO;
import com.habitlife.habitlifeapi.model.entity.PlanNutricional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PlanNutricionalMapper {
    private final ModelMapper modelMapper;

    public PlanNutricional convertToEntity(PlanNutricionalRequestDTO PlanNutricionalRequestDTO){
        return modelMapper.map(PlanNutricionalRequestDTO, PlanNutricional.class);
    }

    public PlanNutricionalResponseDTO convertToDTO(PlanNutricional planNutricional) {
        PlanNutricionalResponseDTO responseDTO = modelMapper.map(planNutricional, PlanNutricionalResponseDTO.class);
        responseDTO.setUsuarioId(planNutricional.getUsuario().getId());
        responseDTO.setNombreUsuario(planNutricional.getUsuario().getNombre() + " " +
                planNutricional.getUsuario().getApellidoPaterno() + " " +
                planNutricional.getUsuario().getApellidoMaterno());
        if (planNutricional.getProfesional() != null) {
            responseDTO.setProfesionalId(planNutricional.getProfesional().getId());
            responseDTO.setNombreProfesional(planNutricional.getProfesional().getNombre() + " " +
                    planNutricional.getProfesional().getApellidoPaterno() + " " +
                    planNutricional.getProfesional().getApellidoMaterno());
        }
        return responseDTO;
    }

    public List<PlanNutricionalResponseDTO> convertToListDTO(List<PlanNutricional> planesnutricionales){

        return planesnutricionales.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
