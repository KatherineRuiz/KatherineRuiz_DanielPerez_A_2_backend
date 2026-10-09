package SystemITR.JosueGuinea1A.vehiculos.controller;

import SystemITR.JosueGuinea1A.response.ApiResponse;
import SystemITR.JosueGuinea1A.vehiculos.models.dto.VehiculosDTO;
import SystemITR.JosueGuinea1A.vehiculos.models.service.VehiculosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin
public class VehiculosController {

    private final VehiculosService service;

    public VehiculosController(VehiculosService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehiculosDTO>>> obtenerVehiculos(){
        try {
            List<VehiculosDTO> lista = service.obtenerTodos();
            log.info("Datos de vehiculos consultados.");
            ApiResponse<List<VehiculosDTO>> respuestaExitosa = new ApiResponse<>(true, "Datos de vehiculos obtenidos.", lista);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaExitosa);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error al obtener los vehiculos: " + e.getMessage());
            ApiResponse<List<VehiculosDTO>> respuestaError = new ApiResponse<>(false, "Error al obtener los vehiculos:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
        }
    }
}
