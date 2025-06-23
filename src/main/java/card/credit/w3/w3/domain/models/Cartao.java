package card.credit.w3.w3.domain.models;

import java.io.Serializable;
import java.time.LocalDate;

import java.time.LocalDateTime;

import card.credit.w3.w3.domain.models.enums.BandeiraCartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.domain.models.enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import java.math.BigDecimal;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cartao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroCartao;
	private BigDecimal limite;
	@Enumerated(EnumType.STRING)
	private TipoCartao tipo;
	@Enumerated(EnumType.STRING)
	private BandeiraCartao bandeira;
	private LocalDateTime dataCriacaoCartao = LocalDateTime.now();
	private LocalDate Vencimento;
	private String senhaHash;
	@Enumerated(EnumType.STRING)
	private StatusCartao status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
}
