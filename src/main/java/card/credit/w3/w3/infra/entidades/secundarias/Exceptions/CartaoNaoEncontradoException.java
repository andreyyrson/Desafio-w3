package card.credit.w3.w3.infra.entidades.secundarias.Exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
