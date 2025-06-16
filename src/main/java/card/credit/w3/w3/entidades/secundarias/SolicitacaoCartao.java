package card.credit.w3.w3.entidades.secundarias;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SolicitacaoCartao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "cartao_id", unique = true)
	private Cartao cartaoGerado;
	
	private BigDecimal rendaInformada;
	
	 @Enumerated(EnumType.STRING)
	 private TipoCartao tipo;
	    
	 @Enumerated(EnumType.STRING)
	 private BandeiraCartao bandeira;
	
	 @Enumerated(EnumType.STRING)
	 private StatusCartao status = StatusCartao.SOLICITADO;
	 
	 private LocalDateTime dataSolicitacao = LocalDateTime.now();
	 

}
