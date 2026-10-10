package SystemITR.JosueGuinea1A.clientes.controller;

import SystemITR.JosueGuinea1A.clientes.models.dto.ClientesDTO;
import SystemITR.JosueGuinea1A.clientes.models.service.ClientesService;
import SystemITR.JosueGuinea1A.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClientesController {

    private final ClientesService service;

    public ClientesController(ClientesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientesDTO>> nuevoCliente(@Valid @RequestBody ClientesDTO json){
        try {
            ClientesDTO dto = service.nuevoCliente(json);
            log.info("Nuevo cliente registrado.");
            ApiResponse<ClientesDTO> response = new ApiResponse<>(true, "Cliente registrado", dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al registrar al cliente: " + e.getMessage());
            ApiResponse<ClientesDTO> respuestaError = new ApiResponse<>(false, "Error al registrar al cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientesDTO>>> obtenerClientes(){
        try {
            List<ClientesDTO> lista = service.obtenerTodos();
            log.info("Datos de clientes consultados.");
            ApiResponse<List<ClientesDTO>> respuestaExitosa = new ApiResponse<>(true, "Datos de clientes obtenidos.", lista);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los clientes: " + e.getMessage());
            ApiResponse<List<ClientesDTO>> respuestaError = new ApiResponse<>(false, "Error al obtener los clientes:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientesDTO>> obtenerPorId(@PathVariable Long id){
        try{
            ClientesDTO dto = service.obtenerPorId(id);
            log.info("Datos del cliente con ID: " + id + ", consultados.");
            ApiResponse<ClientesDTO> respuestaExitosa = new ApiResponse<>(true, "Datos del cliente obtenidos.", dto);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ClientesDTO> respuestaError = new ApiResponse<>(false, "Error al obtener los datos del cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarCliente(id);
            if (respuesta){
                log.info("El cliente con ID: " +id+ ", ha sido eliminado.");
                ApiResponse<Void> respuestExitosa = new ApiResponse<>(true, "El cliente ha sido eliminado.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestExitosa);
            }
            ApiResponse<Void> noEncontrado = new ApiResponse<>(false, "El cliente con ID: " +id+ ", no se encontró.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEncontrado);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al eliminar los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "Error al eliminar los datos del cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientesDTO>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClientesDTO dto){
        try{
            ClientesDTO data = service.actualizar(dto,id);
            if (data != null){
                log.info("El cliente con ID: " + id + ", fue actualizado.");
                ApiResponse<ClientesDTO> resuestaExito = new ApiResponse<>(true, "El cliente fue actualizado.", data);
                return ResponseEntity.ok(resuestaExito);
            }
            ApiResponse<ClientesDTO> respuestaFallida = new ApiResponse<>(false, "Proceso no completado, no se encontró al cliente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaFallida);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al editar los datos del cliente con ID: " + id + ". "+ e.getMessage());
            ApiResponse<ClientesDTO> respuestaError = new ApiResponse<>(false, "Error al editar los datos del cliente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
