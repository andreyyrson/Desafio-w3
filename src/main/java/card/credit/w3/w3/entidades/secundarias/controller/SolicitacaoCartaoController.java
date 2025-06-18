package card.credit.w3.w3.entidades.secundarias.controller;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.secundarias.dto.SolicitacaoCartaoRequest;
import card.credit.w3.w3.entidades.secundarias.dto.SolicitacaoResponse;
import card.credit.w3.w3.entidades.secundarias.services.SolicitacaoCartaoService;
import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cartoes/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoCartaoController {

    private final SolicitacaoCartaoService solicitacaoCartaoService;

    @PostMapping
    public ResponseEntity<SolicitacaoResponse> solicitarCartao(
            @RequestBody @Valid SolicitacaoCartaoRequest request) {
        
        TipoCartao tipoCartao = TipoCartao.valueOf(request.tipo().toUpperCase());
        BandeiraCartao bandeira = BandeiraCartao.valueOf(request.bandeira().toUpperCase());
        
        Cartao cartao = solicitacaoCartaoService.solicitarCartao(
                request.clienteId(), 
                tipoCartao,
                bandeira
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SolicitacaoResponse(
                        cartao.getNumeroCartao(),
                        cartao.getStatus().toString(),
                        "Cartão solicitado com sucesso"
                )
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        "SOLICITACAO_INVALIDA"
                ));
    }

    public record ErrorResponse(
            String mensagem,
            String codigoErro
    ) {}
}
