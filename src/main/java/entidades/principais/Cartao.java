package entidades.principais;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import enums.BandeiraCartao;
import enums.StatusCartao;
import enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private TipoCartao tipo;
	private BandeiraCartao bandeira;
	private LocalDate dataCriacaoCartao;
	private LocalDate Vencimento;
	private String senhaHash;
	private StatusCartao status;
	@ManyToOne(optional = false)
	private Cliente cliente;
}
