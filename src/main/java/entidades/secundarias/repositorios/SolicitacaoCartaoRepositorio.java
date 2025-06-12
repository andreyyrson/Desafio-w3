package entidades.secundarias.repositorios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.principais.Cartao;
import entidades.secundarias.SolicitacaoCartao;

@Repository
public interface SolicitacaoCartaoRepositorio extends JpaRepository<SolicitacaoCartao, UUID> {
	Optional<SolicitacaoCartao> findByCartaoGeradoId(UUID cartaoId);
}
