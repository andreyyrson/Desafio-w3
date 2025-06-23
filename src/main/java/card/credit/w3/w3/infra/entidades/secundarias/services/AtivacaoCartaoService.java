package card.credit.w3.w3.infra.entidades.secundarias.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.secundarias.AtivacaoCartao;
import card.credit.w3.w3.infra.entidades.secundarias.repository.AtivacaoCartaoRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AtivacaoCartaoService {
	 private final CartaoRepository cartaoRepository;
	    private final AtivacaoCartaoRepository ativacaoCartaoRepository;

	    @Transactional
	    public void ativarCartao(Long cartaoId, String cpf, String senha) {
	        Cartao cartao = cartaoRepository.findById(cartaoId)
	                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado"));

	        if (!cartao.getCliente().getCpf().equals(cpf)) {
	            throw new IllegalArgumentException("CPF não corresponde ao titular do cartão");
	        }

	        // Transforma a senha informada em hash para comparar
	        String senhaHash = generateHash(senha);

	        if (!cartao.getSenhaHash().equals(senhaHash)) {
	            throw new IllegalArgumentException("Senha incorreta");
	        }

	        if (cartao.getStatus() != StatusCartao.APROVADO &&
	                cartao.getStatus() != StatusCartao.BLOQUEADO_TEMPORARIO) {
	            throw new IllegalArgumentException("O cartão precisa ter sido APROVADO em algum momento para ativação");
	        }

	        cartao.setStatus(StatusCartao.ATIVO);
	        cartaoRepository.save(cartao);

	        AtivacaoCartao ativacao = new AtivacaoCartao();
	        ativacao.setNumeroCartao(cartao.getNumeroCartao());
	        ativacao.setCpf_cliente(cpf);
	        ativacao.setSenhaInicial(senhaHash);
	        ativacao.setDataAtivacao(LocalDateTime.now());
	        ativacao.setCartao(cartao);

	        ativacaoCartaoRepository.save(ativacao);
	    }

	    private String generateHash(String senha) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = digest.digest(senha.getBytes());
	            return HexFormat.of().formatHex(hashBytes);
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Erro ao gerar hash da senha", e);
	        }
	    }
}
