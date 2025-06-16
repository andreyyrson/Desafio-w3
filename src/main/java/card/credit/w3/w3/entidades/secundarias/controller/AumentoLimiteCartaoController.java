package card.credit.w3.w3.entidades.secundarias.controller;

import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.entidades.secundarias.services.SolicitacaoLimiteService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController()
@RequestMapping("/api/cartoes/limite")
public class AumentoLimiteCartaoController {
    @Autowired
    private SolicitacaoLimiteService solicitacaoLimiteService;

    
    public record SolicitacaoLimiteRequest(CartaoDTO cartao, double limiteNovo) {}

    @PostMapping("/solicitar")
    public ResponseEntity<SolicitacaoLimite> aumentoLimiteCartao(@RequestBody @Valid SolicitacaoLimiteRequest request) {
        SolicitacaoLimite solicitacao = solicitacaoLimiteService.solicitar(request.cartao(), request.limiteNovo());
        return ResponseEntity.ok(solicitacao);
    }
}
