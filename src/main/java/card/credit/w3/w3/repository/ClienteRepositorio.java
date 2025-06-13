package card.credit.w3.w3.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.entidades.principais.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
	Optional<Cliente>  findByCpf(String cpf);
}
