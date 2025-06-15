package card.credit.w3.w3.entidades.secundarias.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.secundarias.AtivacaoCartao;
import card.credit.w3.w3.entidades.secundarias.repository.AtivacaoCartaoRepository;
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

        if (!cartao.getSenhaHash().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        if (cartao.getStatus() != StatusCartao.APROVADO) {
            throw new IllegalArgumentException("Cartão precisa estar APROVADO para ativação");
        }

      
        cartao.setStatus(StatusCartao.ATIVO);
        cartaoRepository.save(cartao);

        
        AtivacaoCartao ativacao = new AtivacaoCartao();
        ativacao.setNumeroCartao(cartao.getNumeroCartao());
        ativacao.setCpf(cpf);
        ativacao.setSenhaInicial(senha);
        ativacao.setDataAtivacao(LocalDateTime.now());
        ativacao.setCartao(cartao);
        
        ativacaoCartaoRepository.save(ativacao);
    }
}
