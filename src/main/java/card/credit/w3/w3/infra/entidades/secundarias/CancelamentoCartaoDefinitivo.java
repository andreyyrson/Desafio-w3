package card.credit.w3.w3.infra.entidades.secundarias;

import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import card.credit.w3.w3.domain.models.Cartao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cancelamento_cartao_definitivo")
public class CancelamentoCartaoDefinitivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String cpfCliente;

    @Column(nullable = false)
    private String motivoCancelamento;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    @Column(nullable = false)
    private LocalDate dataCancelamento;

}
