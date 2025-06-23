package card.credit.w3.w3.infra.entidades.secundarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import card.credit.w3.w3.infra.entidades.secundarias.SolicitacaoLimite;

@Repository
public interface SolicitacaoLimiteRepositorio extends JpaRepository<SolicitacaoLimite, Long> {
}
