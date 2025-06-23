package card.credit.w3.w3.infra.entidades.secundarias.services;

import java.util.List;

import org.springframework.stereotype.Service;

import card.credit.w3.w3.infra.entidades.secundarias.dto.HistoricoUnificadoResponse;
import card.credit.w3.w3.infra.entidades.secundarias.repository.HistoricoCartaoViewRepository;

@Service
public class HistoricoCartaoService {

    private final HistoricoCartaoViewRepository repository;

    public HistoricoCartaoService(HistoricoCartaoViewRepository repository) {
        this.repository = repository;
    }

    public List<HistoricoUnificadoResponse> buscarHistorico(String numeroCartao) {
        return repository.buscarPorNumeroCartao(numeroCartao);
    }
}