package card.credit.w3.w3.entidades.secundarias.dto;

import card.credit.w3.w3.enums.TipoOcorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComunicacaoPerdaRouboRequest(
    @NotBlank(message = "Número do cartão é obrigatório")
    String numeroCartao,
    
    @NotBlank(message = "CPF do cliente é obrigatório")
    String cpfCliente,
    
    @NotNull(message = "Tipo de ocorrência é obrigatório")
    TipoOcorrencia tipoOcorrencia,
    
    @NotBlank(message = "Número do boletim de ocorrência é obrigatório")
    String numeroBoletimOcorrencia
) {}