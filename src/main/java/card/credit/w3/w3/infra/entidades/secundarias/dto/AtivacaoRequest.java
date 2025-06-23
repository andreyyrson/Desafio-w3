package card.credit.w3.w3.infra.entidades.secundarias.dto;

public record AtivacaoRequest(
        Long cartaoId,
        String cpf,
        String senha
) {}
