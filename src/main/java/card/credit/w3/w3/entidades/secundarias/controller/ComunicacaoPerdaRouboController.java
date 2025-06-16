package card.credit.w3.w3.entidades.secundarias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.secundarias.dto.ComunicacaoPerdaRouboRequest;
import card.credit.w3.w3.entidades.secundarias.services.ComunicacaoPerdaRouboService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
public class ComunicacaoPerdaRouboController {

    private final ComunicacaoPerdaRouboService comunicacaoService;

    @PostMapping("/perda-roubo")
    public ResponseEntity<String> comunicarPerdaRoubo(
            @Valid @RequestBody ComunicacaoPerdaRouboRequest request) {
        
        comunicacaoService.comunicarPerdaRoubo(
            request.numeroCartao(),
            request.cpfCliente(),
            request.tipoOcorrencia(),
            request.numeroBoletimOcorrencia()
        );
        
        return ResponseEntity.ok("Comunicação de perda/roubo registrada com sucesso. Cartão bloqueado.");
    }
}