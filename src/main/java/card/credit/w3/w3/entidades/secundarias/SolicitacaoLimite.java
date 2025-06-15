package card.credit.w3.w3.entidades.secundarias;

import java.time.LocalDate;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.enums.StatusSolicitacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SolicitacaoLimite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public SolicitacaoLimite(Cartao cartao, double limiteSolicitado) {
        this.cartao = cartao;
        this.limiteSolicitado = limiteSolicitado;
    }

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    @Column(name = "limite_solicitado", nullable = false)
    private double limiteSolicitado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao = LocalDate.now();

    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    public void aprovar() {
        this.status = StatusSolicitacao.APROVADA;
    }

    public void negar() {
        this.status = StatusSolicitacao.NEGADA;
    }
}
