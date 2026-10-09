package SystemITR.JosueGuinea1A.reservas.models.repository;

import SystemITR.JosueGuinea1A.reservas.models.entity.ReservasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ReservasRepository extends JpaRepository<ReservasEntity, Long> {

    //boolean existByFechaReservaAndVehiculo_Id(Date fechaReserva, Long id);
}
