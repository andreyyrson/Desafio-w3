package card.credit.w3.w3.infra.entidades.principais.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.Cliente;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
	List<Cartao> findByCliente(Cliente cliente);

	Cartao findByNumeroCartao(String numeroCartao);

	@Query("SELECT c FROM Cartao c WHERE c.cliente.cpf = :cpf")
	List<Cartao> buscarPorCpf(@Param("cpf") String cpf);
}
