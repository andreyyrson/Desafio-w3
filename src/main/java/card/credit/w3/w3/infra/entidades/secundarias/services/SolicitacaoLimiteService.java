package card.credit.w3.w3.infra.entidades.secundarias.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.infra.entidades.secundarias.Exceptions.AumentoLimiteInvalidoException;
import card.credit.w3.w3.infra.entidades.secundarias.Exceptions.CartaoNaoEncontradoException;
import card.credit.w3.w3.infra.entidades.secundarias.repository.SolicitacaoLimiteRepositorio;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoLimiteService {

    private final CartaoRepository cartaoRepo;
    private final SolicitacaoLimiteRepositorio solicitacaoRepo;

    @Transactional(noRollbackFor = AumentoLimiteInvalidoException.class)
    public void solicitarAumentoLimite(String numeroCartao, String cpf, BigDecimal novoLimite, String justificativa) {
        if (novoLimite == null || novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O novo limite deve ser um valor positivo");
        }

        Cartao cartao = cartaoRepo.findByNumeroCartao(numeroCartao);
        if (cartao == null) {
            throw new CartaoNaoEncontradoException("Cartão não encontrado com número: " + numeroCartao);
        }

        if(cartao.getVencimento() == null || cartao.getVencimento().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new IllegalStateException("Cartão vencido, não é possível solicitar aumento de limite");
        }

        if (!cartao.getStatus().equals(StatusCartao.ATIVO)) {
            throw new IllegalStateException("Cartão precisa estar ATIVO para solicitar aumento de limite");
        }

        BigDecimal minimoAceitavel = cartao.getLimite().multiply(BigDecimal.valueOf(1.2));
        BigDecimal maximoAceitavel = cartao.getLimite().multiply(BigDecimal.valueOf(2.0));
        boolean aumentoValido = novoLimite.compareTo(minimoAceitavel) >= 0 &&
                                novoLimite.compareTo(maximoAceitavel) <= 0;

        SolicitacaoLimite solicitacao = new SolicitacaoLimite();
        solicitacao.setNumeroCartao(cartao.getNumeroCartao());
        solicitacao.setCpfCliente(cartao.getCliente().getCpf());
        solicitacao.setJustificativa(justificativa);
        solicitacao.setLimiteSolicitado(novoLimite);
        solicitacao.setDataCriacao(LocalDateTime.now());

    
        if (aumentoValido) {
            solicitacao.aprovar();
            cartao.setLimite(novoLimite);
            cartaoRepo.save(cartao);
        } else {
            solicitacao.negar();
        }

       
        solicitacaoRepo.save(solicitacao);

        
        if (!aumentoValido) {
            throw new AumentoLimiteInvalidoException(
                "O novo limite deve ser entre 20% e 100% acima do limite atual"
            );
        }
}
}
