package card.credit.w3.w3.entidades.secundarias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.StatusSolicitacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class SolicitacaoLimite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String cpfCliente;

    @Column(name = "limite_solicitado", nullable = false)
    private BigDecimal limiteSolicitado;

    @Column(nullable = false)
    private String justificativa;

    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status = StatusSolicitacao.EM_ANALISE;

    public void aprovar() {
        this.status = StatusSolicitacao.APROVADA;
    }

    public void negar() {
        this.status = StatusSolicitacao.NEGADA;
    }
}
