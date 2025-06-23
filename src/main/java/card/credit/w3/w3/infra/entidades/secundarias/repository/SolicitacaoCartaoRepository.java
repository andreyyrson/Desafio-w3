package card.credit.w3.w3.infra.entidades.secundarias.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.infra.entidades.secundarias.SolicitacaoCartao;

@Repository
public interface SolicitacaoCartaoRepository extends JpaRepository<SolicitacaoCartao, Long> {
	Optional<SolicitacaoCartao> findByCartaoGeradoId(Long cartaoId);
}
