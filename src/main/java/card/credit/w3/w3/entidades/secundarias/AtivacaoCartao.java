package card.credit.w3.w3.entidades.secundarias;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import card.credit.w3.w3.entidades.principais.Cartao;

@Getter
@Setter
@Entity
@Table(name = "ativacoes_cartao")
public class AtivacaoCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao", nullable = false)
    private String numeroCartao;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "senha_inicial", nullable = false, length = 6)
    private String senhaInicial;

    @Column(name = "data_ativacao", nullable = false)
    private LocalDateTime dataAtivacao;

    public AtivacaoCartao() {}

    public AtivacaoCartao(String numeroCartao, String cpf, String senhaInicial, LocalDateTime dataAtivacao) {
        this.numeroCartao = numeroCartao;
        this.cpf = cpf;
        this.senhaInicial = senhaInicial;
        this.dataAtivacao = dataAtivacao;
    }

    private Cartao cartao;

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
        if (cartao != null) {
            this.numeroCartao = cartao.getNumeroCartao();
        }
    }

    
}