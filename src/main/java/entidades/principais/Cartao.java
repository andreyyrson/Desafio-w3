package entidades.principais;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import enums.BandeiraCartao;
import enums.StatusCartao;
import enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
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
	private UUID id;
	private String numero;
	private double limite;
	@Enumerated(EnumType.STRING)
	private TipoCartao tipo;
	@Enumerated(EnumType.STRING)
	private BandeiraCartao bandeira;
	private LocalDate dataCriacaoCartao = LocalDate.now();
	private LocalDate Vencimento;
	private String senhaHash;
	@Enumerated(EnumType.STRING)
	private StatusCartao status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
}
