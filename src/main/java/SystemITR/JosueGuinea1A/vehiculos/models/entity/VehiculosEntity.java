package SystemITR.JosueGuinea1A.vehiculos.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "vehiculos")
public class VehiculosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id;
    @Column(name = "marca", nullable = false)
    private String marca;
    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "capacidad", nullable = false)
    private int capacidad;
    @Column(name = "precio_dia", nullable = false)
    private double precioDia;
    @Column(name = "placa")
    private String placa;
}
