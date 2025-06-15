package card.credit.w3.w3.entidades.secundarias.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepositorio;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.entidades.secundarias.repository.SolicitacaoLimiteRepositorio;

@Service
public class SolicitacaoLimiteService {
    
    @Autowired
    private SolicitacaoLimiteRepositorio solicitacaoLimiteRepositorio;
    @Autowired
    private CartaoRepositorio cartaoRepo;
    
    public SolicitacaoLimite solicitar(CartaoDTO cartao, double novoLimite) {
        Cartao cartaoExistente = cartaoRepo.findByNumero(cartao.numero());

        SolicitacaoLimite solicitacao = new SolicitacaoLimite(cartaoExistente, novoLimite);

        if (podeAumentar(solicitacao)) {
            solicitacao.aprovar();
        } else {
            solicitacao.negar();
        }

        return solicitacaoLimiteRepositorio.save(solicitacao);
    }

    public boolean podeAumentar(SolicitacaoLimite solicitacao) {
        Cartao cartao = cartaoRepo.findByNumero(solicitacao.getCartao().getNumero());
        if (cartao == null) {
            return false;
        }

        double limiteAtual = cartao.getLimite();
        double novoLimite = solicitacao.getLimiteSolicitado();

        return novoLimite <= limiteAtual * 1.2;
    }
}
