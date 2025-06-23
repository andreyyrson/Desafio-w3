package card.credit.w3.w3.infra.entidades.secundarias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.credit.w3.w3.infra.entidades.secundarias.dto.CadastroSenhaRequest;
import card.credit.w3.w3.infra.entidades.secundarias.dto.RedefinicaoSenhaRequest;
import card.credit.w3.w3.infra.entidades.secundarias.services.CadastroRedefinicaoSenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/cartoes/senha")
@RequiredArgsConstructor
@Tag(name = "Cadastro e Redefinição")
public class CadastroRedefinicaoSenhaController {

	private final CadastroRedefinicaoSenhaService cadastroRedefinicaoSenhaService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de uma senha no cartão após solicitação.")
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
    @Operation(summary = "Redefinição da senha de um cartão já ativo.")
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
