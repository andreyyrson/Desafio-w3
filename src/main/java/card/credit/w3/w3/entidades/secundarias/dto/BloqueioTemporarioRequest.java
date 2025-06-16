package card.credit.w3.w3.entidades.secundarias.dto;

import card.credit.w3.w3.enums.MotivoBloqueioTemporario;

public record BloqueioTemporarioRequest(
        Long cartaoId,
        String cpf,
        MotivoBloqueioTemporario motivo
) {}