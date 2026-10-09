package SystemITR.JosueGuinea1A.reservas.models.entity;

import SystemITR.JosueGuinea1A.clientes.models.entity.ClientesEntity;
import SystemITR.JosueGuinea1A.vehiculos.models.entity.VehiculosEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reservas")
public class ReservasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;
    @Column(name = "nombre_reserva", nullable = false)
    private String nombreReserva;
    @Column(name = "fecha_reserva", nullable = false)
    private Date fechaReserva;
    @Column(name = "cantidad_pasajeros", nullable = false)
    private int cantidadPasajeros;
    @Column(name = "cantidad_dias", nullable = false, unique = true)
    private int cantidadDias;
    @Column(name = "estado")
    private String estado;
    @Column(name = "total_pago")
    private double totalPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClientesEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private VehiculosEntity vehiculo;

}
