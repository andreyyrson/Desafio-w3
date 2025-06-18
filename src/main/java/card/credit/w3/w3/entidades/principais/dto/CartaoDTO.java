package card.credit.w3.w3.entidades.principais.dto;

import java.time.LocalDate;

import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoCartao;

public record CartaoDTO(String numeroCartao,
	    TipoCartao tipo,
	    BandeiraCartao bandeira,
	    LocalDate dataCriacao,
	    StatusCartao status) {

}
