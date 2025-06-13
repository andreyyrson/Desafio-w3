package card.credit.w3.w3.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.repository.AtivacaoCartaoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtivacaoCartaoService {

    private final AtivacaoCartaoRepositorio cartaoRepo;

    @Transactional
    public String ativarCartao(String numeroCartao, String cpf, String senhaInicial) {
        Cartao cartao = cartaoRepo.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado."));

        if (!cartao.getCliente().getCpf().equals(cpf)) {
            throw new RuntimeException("CPF inválido para este cartão.");
        }

        if (cartao.getStatus() != StatusCartao.APROVADO || cartao.getStatus() != StatusCartao.ENTREGUE) {
            throw new RuntimeException("Cartão deve estar com status APROVADO ou ENTREGUE para ser ativado.");
        }

        cartao.setSenhaHash(senhaInicial);
        cartao.setStatus(StatusCartao.ATIVO);

        cartaoRepo.save(cartao);

        return "Cartão " + numeroCartao + " ativado com sucesso.";
    }
}
