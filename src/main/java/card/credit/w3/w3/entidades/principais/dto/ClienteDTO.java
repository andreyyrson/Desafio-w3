package card.credit.w3.w3.entidades.principais.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteDTO(long id,
	    LocalDate dataNascimento,
	    String nome,
	    String cpf,
	    double rendaMensal) {

}
