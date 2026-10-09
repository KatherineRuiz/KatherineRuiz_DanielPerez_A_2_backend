package SystemITR.JosueGuinea1A.clientes.models.repository;

import SystemITR.JosueGuinea1A.clientes.models.entity.ClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {
}
