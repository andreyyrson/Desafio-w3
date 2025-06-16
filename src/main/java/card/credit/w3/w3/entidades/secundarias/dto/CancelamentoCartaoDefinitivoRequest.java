package card.credit.w3.w3.entidades.secundarias.dto;

public record CancelamentoCartaoDefinitivoRequest(
        String numeroCartao,
        String cpf,
        String motivoCancelamento
) {
}
