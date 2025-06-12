package entidades.principais;

import java.time.LocalDate;
import java.util.UUID;

import enums.BandeiraCartao;
import enums.StatusCartao;
import enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	private LocalDate dataNascimento;
	private String name;
	private String cpf;
	private double rendaMensal;
	private LocalDate dataCriacaoConta = LocalDate.now();
}
