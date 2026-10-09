package SystemITR.JosueGuinea1A.vehiculos.models.service;


import SystemITR.JosueGuinea1A.vehiculos.models.dto.VehiculosDTO;
import SystemITR.JosueGuinea1A.vehiculos.models.entity.VehiculosEntity;
import SystemITR.JosueGuinea1A.vehiculos.models.repository.VehiculosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculosService {

    private final VehiculosRepository repo;

    private VehiculosDTO convertirADTO(VehiculosEntity entity){
        VehiculosDTO dto = new VehiculosDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        dto.setCapacidad(entity.getCapacidad());
        dto.setPrecioDia(entity.getPrecioDia());
        dto.setPlaca(entity.getPlaca());
        return dto;
    }

    public List<VehiculosDTO> obtenerTodos(){
        List<VehiculosEntity> entidades = repo.findAll();
        List<VehiculosDTO> dtos = new ArrayList<>();
        for (VehiculosEntity entity : entidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }
}
