package card.credit.w3.w3.infra.entidades.secundarias.controller;

import card.credit.w3.w3.domain.models.enums.MotivoBloqueioTemporario;
import card.credit.w3.w3.infra.entidades.secundarias.dto.BloqueioTemporarioRequest;
import card.credit.w3.w3.infra.entidades.secundarias.services.BloqueioTemporarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/bloqueio-temporario")
@RequiredArgsConstructor
@Tag(name = "Bloqueio Temporário")
public class BloqueioTemporarioController {

    private final BloqueioTemporarioService bloqueioTemporarioService;

    @PostMapping
    @Operation(summary = "Bloqueia o cartão temporariamente de realizar qualquer ação, mas podendo ser ativado novamente.")
    public ResponseEntity<Void> bloquearTemporariamente(
            @RequestBody @Valid BloqueioTemporarioRequest request) {
        
        bloqueioTemporarioService.bloquearTemporariamente(
                request.cartaoId(),
                request.cpf(),
                request.motivo()
        );
        
        return ResponseEntity.ok().build();
    }
}