package card.credit.w3.w3.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import card.credit.w3.w3.domain.models.enums.BandeiraCartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.domain.models.enums.TipoCartao;
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
	private Long id;
	private LocalDate dataNascimento;
	private String name;
	private String cpf;
	private double rendaMensal;
	private LocalDateTime dataCriacaoConta = LocalDateTime.now();
}
