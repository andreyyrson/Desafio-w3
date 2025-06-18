package card.credit.w3.w3.entidades.secundarias.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import card.credit.w3.w3.entidades.secundarias.dto.HistoricoUnificadoResponse;

@org.springframework.stereotype.Repository
public class HistoricoCartaoViewRepository implements Repository<HistoricoUnificadoResponse, Long> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HistoricoCartaoViewRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HistoricoUnificadoResponse> buscarPorNumeroCartao(String numeroCartao) {
        String sql = """
            SELECT cpf_cliente, tipo_acao, detalhes, data_acao, numero_cartao
            FROM vw_historico_cartao_cliente
            WHERE numero_cartao = :numeroCartao
            ORDER BY data_acao DESC
        """;

        return jdbcTemplate.query(sql, Map.of("numeroCartao", numeroCartao), rowMapper);
    }

    private final RowMapper<HistoricoUnificadoResponse> rowMapper = (rs, rowNum) -> new HistoricoUnificadoResponse(
        rs.getString("cpf_cliente"),
        rs.getString("numero_cartao"),
        rs.getString("tipo_acao"),
        rs.getTimestamp("data_acao").toLocalDateTime(),
        rs.getString("detalhes")
    );
}