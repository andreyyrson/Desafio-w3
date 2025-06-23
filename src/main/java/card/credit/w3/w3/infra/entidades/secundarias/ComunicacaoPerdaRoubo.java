package card.credit.w3.w3.infra.entidades.secundarias;

import java.time.LocalDateTime;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.enums.TipoOcorrencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comunicacoes_perda_roubo")
public class ComunicacaoPerdaRoubo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String cpfCliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcorrencia tipoOcorrencia;

    @Column(nullable = false)
    private String numeroBoletimOcorrencia;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}