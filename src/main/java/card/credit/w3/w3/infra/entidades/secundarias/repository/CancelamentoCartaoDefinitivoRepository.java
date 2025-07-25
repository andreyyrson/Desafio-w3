package card.credit.w3.w3.infra.entidades.secundarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import card.credit.w3.w3.infra.entidades.secundarias.CancelamentoCartaoDefinitivo;

public interface CancelamentoCartaoDefinitivoRepository extends JpaRepository<CancelamentoCartaoDefinitivo, Long> {
    
    CancelamentoCartaoDefinitivo findByNumeroCartao(String numeroCartao);
}
