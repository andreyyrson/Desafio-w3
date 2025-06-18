package card.credit.w3.w3.entidades.secundarias;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import card.credit.w3.w3.entidades.principais.Cartao;

@Data
@Entity
@Table(name = "ativacoes_cartao")
public class AtivacaoCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao", nullable = false)
    private String numeroCartao;

    @Column(name = "cpf_cliente", nullable = false)
    private String cpf_cliente;

    @Column(name = "senha_inicial", nullable = false, length = 6)
    private String senhaInicial;

    @Column(name = "data_ativacao", nullable = false)
    private LocalDateTime dataAtivacao;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}