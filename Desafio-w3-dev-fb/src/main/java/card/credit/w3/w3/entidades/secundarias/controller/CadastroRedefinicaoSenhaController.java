package card.credit.w3.w3.entidades.secundarias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.entidades.secundarias.dto.CadastroSenhaRequest;
import card.credit.w3.w3.entidades.secundarias.dto.RedefinicaoSenhaRequest;
import card.credit.w3.w3.entidades.secundarias.services.CadastroRedefinicaoSenhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/cartoes/senha")
@RequiredArgsConstructor
public class CadastroRedefinicaoSenhaController {

	private final CadastroRedefinicaoSenhaService cadastroRedefinicaoSenhaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<MensagemResponse> cadastrarSenha(
            @RequestBody @Valid CadastroSenhaRequest request) {
        
        cadastroRedefinicaoSenhaService.cadastrarOuRedefinirSenha(
                request.cartaoId(),
                request.cpf(),
                request.senha(),
                true 
        );
        
        return ResponseEntity.ok(
                new MensagemResponse("Senha cadastrada com sucesso e cartão aprovado")
        );
    }

    @PostMapping("/redefinir")
    public ResponseEntity<MensagemResponse> redefinirSenha(
            @RequestBody @Valid RedefinicaoSenhaRequest request) {
        
        cadastroRedefinicaoSenhaService.cadastrarOuRedefinirSenha(
                request.cartaoId(),
                request.cpf(),
                request.senha(),
                false 
        );
        
        return ResponseEntity.ok(
                new MensagemResponse("Senha redefinida com sucesso")
        );
    }

    public record MensagemResponse(String mensagem) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new MensagemResponse(ex.getMessage())
        );
    }
}
