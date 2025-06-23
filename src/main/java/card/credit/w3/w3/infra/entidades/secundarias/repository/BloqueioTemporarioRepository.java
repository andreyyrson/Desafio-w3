package card.credit.w3.w3.infra.entidades.secundarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import card.credit.w3.w3.infra.entidades.secundarias.BloqueioTemporario;

public interface BloqueioTemporarioRepository extends JpaRepository<BloqueioTemporario, Long> {
	    boolean existsByCartaoId(Long cartaoId);
}