package card.credit.w3.w3.entidades.secundarias.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.secundarias.services.SolicitacaoCartaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cartoes")
@RequiredArgsConstructor
public class SocilitacaoCartaoController {

	 private final SolicitacaoCartaoService solicitacaoCartaoService;

	    @PostMapping("/solicitar")
	    public ResponseEntity<?> solicitarCartao(@RequestBody SolicitacaoCartaoRequest request) {
	        try {
	            String resultado = solicitacaoCartaoService.solicitarCartao(
	                    request.cpf(),
	                    request.nome(),
	                    request.nascimento(),
	                    request.renda(),
	                    request.tipo(),
	                    request.bandeira()
	            );
	            return ResponseEntity.ok(new SolicitacaoResponse(resultado));
	        } catch (RuntimeException e) {
	            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
	        }
	    }

	    
	    public record SolicitacaoCartaoRequest(
	            String cpf,
	            String nome,
	            LocalDate nascimento,
	            double renda,
	            String tipo,
	            String bandeira
	    ) {}

	    public record SolicitacaoResponse(String mensagem) {}

	    public record ErrorResponse(String erro) {}
}
