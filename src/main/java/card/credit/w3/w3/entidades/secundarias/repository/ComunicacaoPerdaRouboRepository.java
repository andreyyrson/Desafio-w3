package card.credit.w3.w3.entidades.secundarias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import card.credit.w3.w3.entidades.secundarias.ComunicacaoPerdaRoubo;

public interface ComunicacaoPerdaRouboRepository extends JpaRepository<ComunicacaoPerdaRoubo, Long> {
	Optional<ComunicacaoPerdaRoubo> findByCartaoId(Long id);
}