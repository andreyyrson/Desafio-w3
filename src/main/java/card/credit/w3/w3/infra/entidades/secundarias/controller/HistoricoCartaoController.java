package card.credit.w3.w3.infra.entidades.secundarias.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.infra.entidades.secundarias.dto.HistoricoUnificadoResponse;
import card.credit.w3.w3.infra.entidades.secundarias.services.HistoricoCartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/historico-cartao")
@Tag(name = "Histórico")
public class HistoricoCartaoController {

    private final HistoricoCartaoService service;

    public HistoricoCartaoController(HistoricoCartaoService service) {
        this.service = service;
    }

    @GetMapping("/{numeroCartao}")
    @Operation(summary = "Retorna todas as operações já realizadas em um cartão ao longo do tempo.")
    public ResponseEntity<List<HistoricoUnificadoResponse>> buscar(@PathVariable String numeroCartao) {
        List<HistoricoUnificadoResponse> historico = service.buscarHistorico(numeroCartao);
        return ResponseEntity.ok(historico);
    }
}
