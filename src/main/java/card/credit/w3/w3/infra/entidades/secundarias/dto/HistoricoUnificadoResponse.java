package card.credit.w3.w3.infra.entidades.secundarias.dto;

import java.time.LocalDateTime;

public record HistoricoUnificadoResponse(
    String cpfCliente,
    String numeroCartao,
    String tipoAcao,
    LocalDateTime dataAcao,
    String detalhes
) {}