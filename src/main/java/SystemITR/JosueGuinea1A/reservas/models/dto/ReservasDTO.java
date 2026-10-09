package SystemITR.JosueGuinea1A.reservas.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class ReservasDTO {

    private Long id;
    @NotBlank(message = "El nombre de la reserva es obligatorio.")
    @Size(max = 100, message = "El nombre no puede ser mayor a 100 caracteres.")
    private String nombreReserva;
    @NotNull(message = "La fecha no puede ser nula.")
    @FutureOrPresent(message = "La fecha debe ser actual o futura.")
    private Date fechaReserva;
    @NotNull(message = "La cantidad de pasajeros no puede ser nula.")
    @Positive(message = "La cantidad de pasajeros debe ser mayor o igual a 1.")
    private int cantidadPasajeros;
    @NotNull(message = "La cantidad de días no puede ser nula.")
    @Positive(message = "La cantidad de días debe ser mayor o igual a 1.")
    private int cantidadDias;
    @NotBlank(message = "El estado de la reserva es obligatorio.")
    @Size(max = 20, message = "El estado no puede ser mayor a 20 caracteres.")
    private String estado;
    private double totalPago;
    @NotNull(message = "El id del cliente es obligatorio.")
    private Long idCliente;
    @NotNull(message = "El id del vehículo es obligatorio.")
    private Long idVehiculo;

    //Campos adicionales
    private String nombre;
    private String apellido;
    private String placa;

}
