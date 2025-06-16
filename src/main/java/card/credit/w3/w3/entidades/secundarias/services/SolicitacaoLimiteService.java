package card.credit.w3.w3.entidades.secundarias.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepositorio;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.entidades.secundarias.Exceptions.AumentoLimiteInvalidoException;
import card.credit.w3.w3.entidades.secundarias.Exceptions.CartaoNaoEncontradoException;
import card.credit.w3.w3.entidades.secundarias.repository.SolicitacaoLimiteRepositorio;
import card.credit.w3.w3.enums.StatusCartao;

@Service
public class SolicitacaoLimiteService {

    @Autowired
    private SolicitacaoLimiteRepositorio solicitacaoLimiteRepositorio;

    @Autowired
    private CartaoRepositorio cartaoRepo;

    public SolicitacaoLimite solicitar(CartaoDTO cartaoDTO, double novoLimite) {
        Cartao cartao = cartaoRepo.findByNumero(cartaoDTO.numero());

        if (cartao == null) {
            throw new CartaoNaoEncontradoException("Cartão não encontrado com número: " + cartaoDTO.numero());
        }

        if (cartao.getStatus() != StatusCartao.APROVADO) {
            throw new IllegalStateException("Cartão não está aprovado para aumento de limite");
        }

        SolicitacaoLimite solicitacao = new SolicitacaoLimite(cartao, novoLimite);

        if (!aumentoSuperiorA20PorCento(cartao, novoLimite)) {
            solicitacao.negar();
            solicitacaoLimiteRepositorio.save(solicitacao);
            throw new AumentoLimiteInvalidoException("O novo limite deve ser pelo menos 20% maior que o atual");
        }

        solicitacao.aprovar();
        cartao.setLimite(novoLimite);

        cartaoRepo.save(cartao);
        return solicitacaoLimiteRepositorio.save(solicitacao);
    }

    private boolean aumentoSuperiorA20PorCento(Cartao cartao, double novoLimite) {
        return novoLimite >= cartao.getLimite() * 1.2;
    }
}
