package card.credit.w3.w3.entidades.secundarias.controller;

import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.entidades.secundarias.services.SolicitacaoLimiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController()
@RequestMapping("/api/cartoes/limite")
public class AumentoLimiteCartaoController {
    @Autowired
    private SolicitacaoLimiteService solicitacaoLimiteService;

    @PostMapping("/solicitar")
public SolicitacaoLimite aumentoLimiteCartao(@RequestBody SolicitacaoLimiteRequest request) {
    return solicitacaoLimiteService.solicitar(request.cartao(), request.limite());
}
    
    public record SolicitacaoLimiteRequest(CartaoDTO cartao, double limite) {}
}
