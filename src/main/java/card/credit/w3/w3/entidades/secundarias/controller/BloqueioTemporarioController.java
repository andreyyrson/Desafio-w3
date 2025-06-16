package card.credit.w3.w3.entidades.secundarias.controller;

import card.credit.w3.w3.entidades.secundarias.dto.BloqueioTemporarioRequest;
import card.credit.w3.w3.entidades.secundarias.services.BloqueioTemporarioService;
import card.credit.w3.w3.enums.MotivoBloqueioTemporario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/bloqueio-temporario")
@RequiredArgsConstructor
public class BloqueioTemporarioController {

    private final BloqueioTemporarioService bloqueioTemporarioService;

    @PostMapping
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