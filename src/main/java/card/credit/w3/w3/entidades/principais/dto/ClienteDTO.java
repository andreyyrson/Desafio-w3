package card.credit.w3.w3.entidades.principais.dto;

import java.time.LocalDate;

public record ClienteDTO(long id,
	    LocalDate dataNascimento,
	    String nome,
	    String cpf,
	    double rendaMensal,
	    LocalDate dataCriacaoConta) {

}
