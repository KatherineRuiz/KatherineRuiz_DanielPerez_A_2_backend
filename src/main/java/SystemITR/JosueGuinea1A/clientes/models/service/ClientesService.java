package SystemITR.JosueGuinea1A.clientes.models.service;

import SystemITR.JosueGuinea1A.clientes.models.dto.ClientesDTO;
import SystemITR.JosueGuinea1A.clientes.models.entity.ClientesEntity;
import SystemITR.JosueGuinea1A.clientes.models.repository.ClientesRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientesService {

    private final ClientesRepository repo;

    private ClientesEntity convertirAEntity(ClientesDTO dto){
        ClientesEntity entity = new ClientesEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setDireccion(dto.getDireccion());
        return entity;
    }

    private ClientesDTO convertirADTO(ClientesEntity entity){
        ClientesDTO dto = new ClientesDTO();
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setDireccion(entity.getDireccion());
        return dto;
    }

    public ClientesDTO nuevoCliente(ClientesDTO dto){
        ClientesEntity entity = convertirAEntity(dto);
        ClientesEntity enititySave = repo.save(entity);
        return convertirADTO(enititySave);
    }

    public List<ClientesDTO> obtenerTodos(){
        List<ClientesEntity> entidades = repo.findAll();
        List<ClientesDTO> dtos = new ArrayList<>();
        for (ClientesEntity entity : entidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public ClientesDTO obtenerPorId(Long id){
        Optional<ClientesEntity> entidadOpcional = repo.findById(id);
        if (entidadOpcional.isPresent()){
            return convertirADTO(entidadOpcional.get());
        }
        return null;
    }

    public boolean eliminarCliente(Long id){
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public ClientesDTO actualizar(@Valid ClientesDTO dto, Long id){
            Optional<ClientesEntity> registroExistente = repo.findById(id);
            if (registroExistente.isPresent()){
                ClientesEntity entidad = registroExistente.get();
                entidad.setNombre(dto.getNombre());
                entidad.setApellido(dto.getApellido());
                entidad.setTelefono(dto.getTelefono());
                entidad.setEmail(dto.getEmail());
                entidad.setDireccion(dto.getDireccion());
                ClientesEntity datosGuardados = repo.save(entidad);
                return convertirADTO(datosGuardados);
            }
            return null;
    }
}
