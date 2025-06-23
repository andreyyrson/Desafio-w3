package card.credit.w3.w3.infra.entidades.secundarias.dto;

import card.credit.w3.w3.domain.models.enums.MotivoBloqueioTemporario;

public record BloqueioTemporarioRequest(
        Long cartaoId,
        String cpf,
        MotivoBloqueioTemporario motivo
) {}