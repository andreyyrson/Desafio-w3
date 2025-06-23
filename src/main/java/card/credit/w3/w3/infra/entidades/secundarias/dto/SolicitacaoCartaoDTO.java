package card.credit.w3.w3.infra.entidades.secundarias.dto;

import card.credit.w3.w3.domain.models.enums.BandeiraCartao;
import card.credit.w3.w3.domain.models.enums.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record SolicitacaoCartaoDTO( @NotBlank
	    String cpf,
	    
	    @NotNull
	    TipoCartao tipo,
	    
	    @NotNull
	    BandeiraCartao bandeira,
	    
	    @Positive
	    double rendaMensal) {

}
