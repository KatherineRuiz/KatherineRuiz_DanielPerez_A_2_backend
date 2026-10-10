package SystemITR.JosueGuinea1A.reservas.controller;

import SystemITR.JosueGuinea1A.reservas.models.dto.ReservasDTO;
import SystemITR.JosueGuinea1A.reservas.models.service.ReservasService;
import SystemITR.JosueGuinea1A.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin
public class ReservasController {

    private final ReservasService service;

    public ReservasController(ReservasService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservasDTO>> nuevReserva(@Valid @RequestBody ReservasDTO json){
        try {
            ReservasDTO dto = service.nuevaReserva(json);
            log.info("Nueva reserva registrada.");
            ApiResponse<ReservasDTO> response = new ApiResponse<>(true, "Reserva registrado", dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al registrar la reserva " + e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al registrar la reserva");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservasDTO>>> obtenerReserva(){
        try {
            List<ReservasDTO> lista = service.obtenerTodos();
            log.info("Datos de reservas consultados.");
            ApiResponse<List<ReservasDTO>> respuestaExitosa = new ApiResponse<>(true, "Datos de reservas obtenidos.", lista);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener las reservas: " + e.getMessage());
            ApiResponse<List<ReservasDTO>> respuestaError = new ApiResponse<>(false, "Error al obtener las reservas");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservasDTO>> obtenerPorId(@PathVariable Long id){
        try{
            ReservasDTO dto = service.obtenerPorId(id);
            log.info("Datos de la reserva con ID: " + id + ", consultados.");
            ApiResponse<ReservasDTO> respuestaExitosa = new ApiResponse<>(true, "Datos de reservas obtenidos.", dto);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los datos de la reserva con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al obtener los datos de la reserva con ID: " + id + ". " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarReserva(id);
            if (respuesta){
                log.info("El cliente con ID: " +id+ ", ha sido eliminado.");
                ApiResponse<Void> respuestExitosa = new ApiResponse<>(true, "La reserva ha sido eliminada.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestExitosa);
            }
            ApiResponse<Void> noEncontrado = new ApiResponse<>(false, "La reserva no se encontró.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEncontrado);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al eliminar los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "Error al eliminar la reserva del cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservasDTO>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ReservasDTO dto){
        try{
            ReservasDTO data = service.actualizar(dto,id);
            if (data != null){
                log.info("La reserva con ID: " + id + ", fue actualizada.");
                ApiResponse<ReservasDTO> resuestaExito = new ApiResponse<>(true, "La reserva fue actualizada.", data);
                return ResponseEntity.ok(resuestaExito);
            }
            ApiResponse<ReservasDTO> respuestaFallida = new ApiResponse<>(false, "Proceso no completado, no se encontró la reserva");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaFallida);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al editar los datos de la reserva con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al editar los datos de la reserva");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
