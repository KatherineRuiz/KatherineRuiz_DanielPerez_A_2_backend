package SystemITR.JosueGuinea1A.vehiculos.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehiculosDTO {

    private Long id;
    private String marca;
    private String modelo;
    private int capacidad;
    private double precioDia;
    private String placa;
}
