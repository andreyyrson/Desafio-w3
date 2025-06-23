package card.credit.w3.w3.infra.entidades.principais.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.domain.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Optional<Cliente>  findByCpf(String cpf);
}
