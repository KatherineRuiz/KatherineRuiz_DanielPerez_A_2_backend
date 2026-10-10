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
        dto.setId(entity.getId());
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
        if (repo.existsByFechaReservaAndVehiculo_Id(dto.getFechaReserva(), dto.getIdVehiculo())){
            throw new RuntimeException("Ya existe una reserva para este vehículo en esta fecha.");
        }
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

    public ReservasDTO actualizar(ReservasDTO dto, Long id){
        if (!List.of("CONFIRMADA", "PENDIENTE", "CANCELADA", "FINALIZADA").contains(dto.getEstado())) {
            throw new RuntimeException("El estado de la reserva es inválido");
        }

        Optional<ReservasEntity> existente = repo.findById(id);
        if (existente.isEmpty()) {
            return null;
        }
        ReservasEntity entity = existente.get();

        VehiculosEntity vehiculo = vehiculosRepo.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("El vehículo no fue encontrado."));
        ClientesEntity cliente = clientesRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("El cliente no fue encontrado."));

        if (dto.getCantidadPasajeros() > vehiculo.getCapacidad()) {
            throw new RuntimeException("La cantidad de pasajeros es mayor a la capacidad del vehículo");
        }

        boolean cambioFecha = entity.getFechaReserva().getTime() != dto.getFechaReserva().getTime();
        boolean cambioVehiculo = !entity.getVehiculo().getId().equals(dto.getIdVehiculo());
        if ((cambioFecha || cambioVehiculo)
                && repo.existsByFechaReservaAndVehiculo_Id(dto.getFechaReserva(), dto.getIdVehiculo())) {
            throw new RuntimeException("Ya existe una reserva para este vehículo en esta fecha.");
        }

        entity.setNombreReserva(dto.getNombreReserva());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setCantidadPasajeros(dto.getCantidadPasajeros());
        entity.setCantidadDias(dto.getCantidadDias());
        entity.setEstado(dto.getEstado());
        entity.setCliente(cliente);
        entity.setVehiculo(vehiculo);
        entity.setTotalPago(dto.getCantidadDias() * vehiculo.getPrecioDia());

        return convertirADTO(repo.save(entity));
    }
}
