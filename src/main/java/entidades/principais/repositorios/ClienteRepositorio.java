package entidades.principais.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import entidades.principais.Cliente;


public interface ClienteRepositorio extends JpaRepository<Cliente, UUID> {
	Cliente findByCpf(String cpf);
}
