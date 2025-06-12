package entidades.secundarias.dto;

import enums.BandeiraCartao;
import enums.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


public record SolicitacaoCartaoDTO( @NotBlank
	    String cpf,
	    
	    @NotNull
	    TipoCartao tipo,
	    
	    @NotNull
	    BandeiraCartao bandeira,
	    
	    @Positive
	    double rendaMensal) {

}
