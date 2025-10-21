package mack.ps2.estagios.estagios.repository;

import mack.ps2.estagios.estagios.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepo extends JpaRepository<Empresa, Long> {
}
