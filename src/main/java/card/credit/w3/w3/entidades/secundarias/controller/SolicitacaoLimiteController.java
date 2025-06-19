package card.credit.w3.w3.entidades.secundarias.controller;

import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoLimite;
import card.credit.w3.w3.entidades.secundarias.services.SolicitacaoLimiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController()
@RequestMapping("/api/cartoes/limite")
@Tag(name = "Limite")
public class SolicitacaoLimiteController {
    @Autowired
    private SolicitacaoLimiteService solicitacaoLimiteService;

    
    public record SolicitacaoLimiteRequest(@NotNull String numeroCartao, @NotNull String cpf ,@NotNull BigDecimal limiteNovo, @NotNull String justificativa) {}

    @PostMapping
    @Operation(summary = "Aumenta o limite do cartão baseado na renda do usuário e no valor solicitado.")
    public ResponseEntity<String> aumentoLimiteCartao(@RequestBody @Valid SolicitacaoLimiteRequest request) {
        solicitacaoLimiteService.solicitarAumentoLimite(request.numeroCartao(),request.cpf() ,request.limiteNovo(), request.justificativa());
        return ResponseEntity.ok("Limite aumentado com sucesso");
    }
}
