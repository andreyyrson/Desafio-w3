package card.credit.w3.w3.entidades.secundarias.controller;

import card.credit.w3.w3.entidades.secundarias.dto.AtivacaoRequest;
import card.credit.w3.w3.entidades.secundarias.services.AtivacaoCartaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/ativacao")
@RequiredArgsConstructor
public class AtivacaoCartaoController {

    private final AtivacaoCartaoService ativacaoCartaoService;

    @PostMapping
    public ResponseEntity<Void> ativarCartao(
            @RequestBody @Valid AtivacaoRequest request) {
        
        ativacaoCartaoService.ativarCartao(
                request.cartaoId(),
                request.cpf(),
                request.senha()
        );
        
        return ResponseEntity.ok().build();
    }

    
}
