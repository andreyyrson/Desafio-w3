package card.credit.w3.w3.infra.entidades.secundarias;

import java.time.LocalDateTime;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
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
@Table(name = "cadastro_redefinicao_senha")
public class CadastroRedefinicaoSenha {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String senhaCartao; 

	    @Column(nullable = false)
	    private LocalDateTime dataCriacao;

	    @ManyToOne
	    @JoinColumn(name = "cartao_id", nullable = false)
	    private Cartao cartao;

	    @Column(nullable = false)
	    private String cpfCliente;

	 
}
