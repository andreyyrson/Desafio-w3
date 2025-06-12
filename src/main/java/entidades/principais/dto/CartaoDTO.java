package entidades.principais.dto;

import java.time.LocalDate;

import enums.BandeiraCartao;
import enums.StatusCartao;
import enums.TipoCartao;

public record CartaoDTO(String numero,
	    TipoCartao tipo,
	    BandeiraCartao bandeira,
	    LocalDate dataCriacao,
	    StatusCartao status) {

}
