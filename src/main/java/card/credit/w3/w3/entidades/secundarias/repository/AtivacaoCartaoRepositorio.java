package card.credit.w3.w3.entidades.secundarias.repository;

import card.credit.w3.w3.entidades.principais.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivacaoCartaoRepositorio extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
