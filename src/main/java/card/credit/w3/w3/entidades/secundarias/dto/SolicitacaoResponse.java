package card.credit.w3.w3.entidades.secundarias.dto;

public record SolicitacaoResponse(
        String numeroCartao,
        String status,
        String mensagem
) {}
