package card.credit.w3.w3.entidades.secundarias;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.enums.MotivoBloqueioTemporario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bloqueio_temporario")
public class BloqueioTemporario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MotivoBloqueioTemporario motivo;

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String cpfCliente;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;
}