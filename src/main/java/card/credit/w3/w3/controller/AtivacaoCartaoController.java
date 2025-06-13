package card.credit.w3.w3.controller;

import card.credit.w3.w3.entidades.secundarias.dto.AtivacaoCartaoDTO;
import card.credit.w3.w3.services.AtivacaoCartaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
public class AtivacaoCartaoController {

    private final AtivacaoCartaoService cartaoService;

    @PostMapping("/ativar")
    public ResponseEntity<?> ativarCartao(@Valid @RequestBody AtivacaoCartaoDTO ativacaoCartaoDTO) {
        try {
            cartaoService.ativarCartao(
                    ativacaoCartaoDTO.numeroCartao(),
                    ativacaoCartaoDTO.cpf(),
                    ativacaoCartaoDTO.senhaInicial()
            );
            return ResponseEntity.ok("Cartão ativado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
