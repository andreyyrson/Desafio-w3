package card.credit.w3.w3.entidades.secundarias.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


public record SolicitacaoCartaoRequest(
		Long clienteId,
        String nome,
        LocalDate nascimento,
        BigDecimal renda,
        String tipo,
        String bandeira
) {}