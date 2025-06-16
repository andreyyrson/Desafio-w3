package card.credit.w3.w3.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import card.credit.w3.w3.entidades.secundarias.Exceptions.AumentoLimiteInvalidoException;
import card.credit.w3.w3.entidades.secundarias.Exceptions.CartaoNaoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoNaoEncontradoException.class)
    public ResponseEntity<String> handleCartaoNaoEncontrado(CartaoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AumentoLimiteInvalidoException.class)
    public ResponseEntity<String> handleLimiteInvalido(AumentoLimiteInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Erro interno: " + ex.getMessage());
    }
}
