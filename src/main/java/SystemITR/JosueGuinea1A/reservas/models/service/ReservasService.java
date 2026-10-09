package SystemITR.JosueGuinea1A.reservas.models.service;

import SystemITR.JosueGuinea1A.clientes.models.entity.ClientesEntity;
import SystemITR.JosueGuinea1A.clientes.models.repository.ClientesRepository;
import SystemITR.JosueGuinea1A.reservas.models.dto.ReservasDTO;
import SystemITR.JosueGuinea1A.reservas.models.entity.ReservasEntity;
import SystemITR.JosueGuinea1A.reservas.models.repository.ReservasRepository;
import SystemITR.JosueGuinea1A.vehiculos.models.entity.VehiculosEntity;
import SystemITR.JosueGuinea1A.vehiculos.models.repository.VehiculosRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservasService {

    private final ReservasRepository repo;
    private final ClientesRepository clientesRepo;
    private final VehiculosRepository vehiculosRepo;

    private ReservasEntity convertirAEntity(ReservasDTO dto){
        ReservasEntity entity = new ReservasEntity();
        entity.setNombreReserva(dto.getNombreReserva());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setCantidadPasajeros(dto.getCantidadPasajeros());
        entity.setCantidadDias(dto.getCantidadDias());
        entity.setEstado(dto.getEstado());
        entity.setTotalPago(dto.getTotalPago());
        if (dto.getIdCliente() != null){
            Optional<ClientesEntity> cliente = clientesRepo.findById(dto.getIdCliente());
            if (cliente != null){
                entity.setCliente(cliente.get());
            }else {
                throw new RuntimeException("El cliente no fue encontrado.");
            }
        }
        if (dto.getIdVehiculo() != null){
            Optional<VehiculosEntity> vehiculo = vehiculosRepo.findById(dto.getIdVehiculo());
            if (vehiculo != null){
                entity.setVehiculo(vehiculo.get());
            }else {
                throw new RuntimeException("El vehículo no fue encontrado.");
            }
        }
        return entity;
    }

    private ReservasDTO convertirADTO(ReservasEntity entity){
        ReservasDTO dto = new ReservasDTO();
        dto.setNombreReserva(entity.getNombreReserva());
        dto.setFechaReserva(entity.getFechaReserva());
        dto.setCantidadPasajeros(entity.getCantidadPasajeros());
        dto.setCantidadDias(entity.getCantidadDias());
        dto.setEstado(entity.getEstado());
        dto.setTotalPago(entity.getTotalPago());
        dto.setIdCliente(entity.getCliente().getId());
        dto.setNombre(entity.getCliente().getNombre());
        dto.setIdVehiculo(entity.getVehiculo().getId());
        dto.setPlaca(entity.getVehiculo().getPlaca());
        return dto;
    }

    public ReservasDTO nuevaReserva(ReservasDTO dto){
        Optional<VehiculosEntity> vehiculo = vehiculosRepo.findById(dto.getIdVehiculo());
        if (dto.getCantidadPasajeros() > vehiculo.get().getCapacidad()) {
            throw new RuntimeException("La cantidad de pasajeros es mayor a la capacidad del vehículo");
        }
//        if (repo.existByFechaReservaAndVehiculo_Id(dto.getFechaReserva(), dto.getIdVehiculo()) == true){
//            throw new RuntimeException("Ya existe una reserva para este vehículo en esta fecha.");
//        }
        ReservasEntity entity = convertirAEntity(dto);
        entity.setEstado("CONFIRMADA");
        entity.setTotalPago(entity.getCantidadDias() * vehiculo.get().getPrecioDia());
        ReservasEntity enititySave = repo.save(entity);
        return convertirADTO(enititySave);
    }

    public List<ReservasDTO> obtenerTodos(){
        List<ReservasEntity> entidades = repo.findAll();
        List<ReservasDTO> dtos = new ArrayList<>();
        for (ReservasEntity entity : entidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public ReservasDTO obtenerPorId(Long id){
        Optional<ReservasEntity> entidadOpcional = repo.findById(id);
        if (entidadOpcional.isPresent()){
            return convertirADTO(entidadOpcional.get());
        }
        return null;
    }

    public boolean eliminarReserva(Long id){
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public ReservasDTO actualizar(@Valid ReservasDTO dto, Long id){
        if (dto.getEstado() != "CONFIRMADA" && dto.getEstado() != "PENDIENTE" && dto.getEstado() != "CANCELADA" && dto.getEstado() != "FINALIZADA"){
            new RuntimeException("El estado de la reserva es inválido");
        }
        Optional<VehiculosEntity> vehiculo = vehiculosRepo.findById(dto.getIdVehiculo());
        if (dto.getCantidadPasajeros() > vehiculo.get().getCapacidad()) {
            throw new RuntimeException("La cantidad de pasajeros es mayor a la capacidad del vehículo");
        }
//        if (repo.existByFechaReservaAndVehiculo_Id(dto.getFechaReserva(), dto.getIdVehiculo()) == true){
//            throw new RuntimeException("Ya existe una reserva para este vehículo en esta fecha.");
//        }
        Optional<ReservasEntity> registroExistente = repo.findById(id);
        if (registroExistente.isPresent()){
            ReservasEntity entity = registroExistente.get();
            dto.setNombreReserva(entity.getNombreReserva());
            dto.setFechaReserva(entity.getFechaReserva());
            dto.setCantidadPasajeros(entity.getCantidadPasajeros());
            dto.setCantidadDias(entity.getCantidadDias());
            dto.setEstado(entity.getEstado());
            dto.setTotalPago(entity.getTotalPago());
            dto.setIdCliente(entity.getCliente().getId());
            ReservasEntity datosGuardados = repo.save(entity);
            return convertirADTO(datosGuardados);
        }
        return null;
    }
}
