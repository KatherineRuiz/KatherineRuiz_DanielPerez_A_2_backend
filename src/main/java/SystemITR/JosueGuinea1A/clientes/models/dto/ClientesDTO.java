package SystemITR.JosueGuinea1A.clientes.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientesDTO {

    private Long id;
    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre no puede ser mayor a 100 caracteres.")
    private String nombre;
    @NotBlank(message = "El apellido del cliente es obligatorio.")
    @Size(min = 2, max = 100, message = "El apellido no puede ser mayor a 100 caracteres.")
    private String apellido;
    @NotBlank(message = "El telefono del cliente es obligatorio.")
    @Size(max = 15, message = "El teléfono no puede ser mayor a 15 caracteres.")
    private String telefono;
    @NotBlank(message = "El email del cliente es obligatorio.")
    @Email(message = "El email debe tener un formato válido.")
    @Size(max = 100, message = "El email no puede ser mayor a 100 caracteres.")
    private String email;
    @Size(min = 5, max = 200, message = "La dirección no puede ser mayor a 200 caracteres")
    private String direccion;


}
