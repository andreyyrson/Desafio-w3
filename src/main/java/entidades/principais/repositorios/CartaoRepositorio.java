package entidades.principais.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import entidades.principais.Cartao;
import entidades.principais.Cliente;

public interface CartaoRepositorio extends JpaRepository<Cartao, UUID> {
	List<Cartao> findByCliente(Cliente cliente);
}
