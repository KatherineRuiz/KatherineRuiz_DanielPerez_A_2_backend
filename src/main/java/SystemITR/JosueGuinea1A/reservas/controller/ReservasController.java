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
    public ResponseEntity<ApiResponse<ReservasDTO>> nuevReserva(@Valid ReservasDTO json){
        try {
            ReservasDTO dto = service.nuevaReserva(json);
            log.info("Nuevo cliente registrado.");
            ApiResponse<ReservasDTO> response = new ApiResponse<>(true, "Cliente registrado", dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al registrar al cliente: " + e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al registrar al cliente:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservasDTO>>> obtenerReserva(){
        try {
            List<ReservasDTO> lista = service.obtenerTodos();
            log.info("Datos de clientes consultados.");
            ApiResponse<List<ReservasDTO>> respuestaExitosa = new ApiResponse<>(true, "Datos de clientes obtenidos.", lista);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los clientes: " + e.getMessage());
            ApiResponse<List<ReservasDTO>> respuestaError = new ApiResponse<>(false, "Error al obtener los clientes:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservasDTO>> obtenerPorId(@PathVariable Long id){
        try{
            ReservasDTO dto = service.obtenerPorId(id);
            log.info("Datos del cliente con ID: " + id + ", consultados.");
            ApiResponse<ReservasDTO> respuestaExitosa = new ApiResponse<>(true, "Datos del cliente obtenidos.", dto);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al obtener los datos del cliente con ID: " + id + ". " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarReserva(id);
            if (respuesta){
                log.info("El cliente con ID: " +id+ ", ha sido eliminado.");
                ApiResponse<Void> respuestExitosa = new ApiResponse<>(true, "El cliente con ID: " +id+ ", ha sido eliminado.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestExitosa);
            }
            ApiResponse<Void> noEncontrado = new ApiResponse<>(false, "El cliente con ID: " +id+ ", no se encontró.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEncontrado);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al eliminar los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "Error al eliminar los datos del cliente con ID: " + id + ". " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservasDTO>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ReservasDTO dto){
        try{
            ReservasDTO data = service.actualizar(dto,id);
            if (data != null){
                log.info("El cliente con ID: " + id + ", fue actualizado.");
                ApiResponse<ReservasDTO> resuestaExito = new ApiResponse<>(true, "El cliente con ID: " + id + ", fue actualizado.", data);
                return ResponseEntity.ok(resuestaExito);
            }
            ApiResponse<ReservasDTO> respuestaFallida = new ApiResponse<>(false, "Proceso no completado, no se encontró al cliente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaFallida);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al editar los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ReservasDTO> respuestaError = new ApiResponse<>(false, "Error al editar los datos del cliente con ID: " + id + ". " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
