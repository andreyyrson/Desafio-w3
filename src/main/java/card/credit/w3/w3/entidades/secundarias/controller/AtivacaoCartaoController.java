package card.credit.w3.w3.entidades.secundarias.controller;

import card.credit.w3.w3.entidades.secundarias.controller.CadastroRedefinicaoSenhaController.MensagemResponse;
import card.credit.w3.w3.entidades.secundarias.dto.AtivacaoRequest;
import card.credit.w3.w3.entidades.secundarias.services.AtivacaoCartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/ativacao")
@RequiredArgsConstructor
@Tag(name = "Ativação")
public class AtivacaoCartaoController {

    private final AtivacaoCartaoService ativacaoCartaoService;

    @PostMapping
    @Operation(summary = "Ativa o cartão para uso e salva a senha inserida no momento do cadastro.")
    public ResponseEntity<MensagemResponse> ativarCartao(
            @RequestBody @Valid AtivacaoRequest request) {
        
        ativacaoCartaoService.ativarCartao(
                request.cartaoId(),
                request.cpf(),
                request.senha()
        );
        return ResponseEntity.ok(
                new MensagemResponse("Cartão ativado com sucesso")
        );
        
    }

    
}
