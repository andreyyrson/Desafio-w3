package card.credit.w3.w3.entidades.secundarias.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SolicitacaoCartaoRequest(
		Long clienteId,
        String nome,
        LocalDate nascimento,
        BigDecimal renda,
        String tipo,
        String bandeira
) {}