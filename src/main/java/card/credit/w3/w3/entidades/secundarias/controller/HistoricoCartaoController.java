package card.credit.w3.w3.entidades.secundarias.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.secundarias.dto.HistoricoUnificadoResponse;
import card.credit.w3.w3.entidades.secundarias.services.HistoricoCartaoService;

@RestController
@RequestMapping("/api/historico-cartao")
public class HistoricoCartaoController {

    private final HistoricoCartaoService service;

    public HistoricoCartaoController(HistoricoCartaoService service) {
        this.service = service;
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<List<HistoricoUnificadoResponse>> buscar(@PathVariable String numeroCartao) {
        List<HistoricoUnificadoResponse> historico = service.buscarHistorico(numeroCartao);
        return ResponseEntity.ok(historico);
    }
}
