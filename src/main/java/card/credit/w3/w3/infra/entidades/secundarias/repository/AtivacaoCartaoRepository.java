package card.credit.w3.w3.infra.entidades.secundarias.repository;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.infra.entidades.secundarias.AtivacaoCartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AtivacaoCartaoRepository extends JpaRepository<AtivacaoCartao, Long> {
    boolean existsByCartaoId(Long cartaoId);
}
