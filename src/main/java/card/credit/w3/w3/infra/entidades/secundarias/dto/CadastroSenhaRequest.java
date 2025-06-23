package card.credit.w3.w3.infra.entidades.secundarias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroSenhaRequest(@NotNull Long cartaoId,
        @NotBlank String cpf,
        @NotBlank @Size(min = 6, max = 6) String senha) {

}
