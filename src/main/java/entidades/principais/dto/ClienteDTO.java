package entidades.principais.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteDTO(UUID id,
	    LocalDate dataNascimento,
	    String nome,
	    String cpf,
	    double rendaMensal,
	    LocalDate dataCriacaoConta) {

}
