package card.credit.w3.w3.entidades.principais.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;

@Repository
public interface CartaoRepositorio extends JpaRepository<Cartao, Long> {
	List<Cartao> findByCliente(Cliente cliente);
}
