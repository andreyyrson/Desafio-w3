package card.credit.w3.w3.infra.entidades.secundarias.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import card.credit.w3.w3.infra.entidades.secundarias.CadastroRedefinicaoSenha;

public interface CadastroRedefinicaoSenhaRepository extends JpaRepository<CadastroRedefinicaoSenha, Long> {
	Optional<CadastroRedefinicaoSenha> findByCartaoId(Long cartaoId);
    List<CadastroRedefinicaoSenha> findByCpfCliente(String cpf);
}
