package card.credit.w3.w3.infra.entidades.secundarias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.infra.entidades.secundarias.dto.CancelamentoCartaoDefinitivoRequest;
import card.credit.w3.w3.infra.entidades.secundarias.services.CancelamentoCartaoDefinitivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cartes/cancelar")
@RequiredArgsConstructor
@Tag(name = "Cancelamento")
public class CancelamentoCartaoDefinitivoController {
    
    private final CancelamentoCartaoDefinitivoService cancelamentoCartaoDefinitivoService;

    @PostMapping
    @Operation(summary = "Muda o status do cartão para cancelado, tornando-o inutilizavel.")
    public ResponseEntity<Void> cancelamentoCartaoDefinitivo(@RequestBody @Valid CancelamentoCartaoDefinitivoRequest request) {
        cancelamentoCartaoDefinitivoService.cancelarCartaoDefinitivo(
                request.numeroCartao(),
                request.cpf(),
                request.motivoCancelamento()
        );
        
        return ResponseEntity.ok().build();
    }
    
}
