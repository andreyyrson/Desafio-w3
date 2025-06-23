package card.credit.w3.w3.infra.springdoc;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(new Info().
				title("Documentação CPS")
				.version("v1")
				.description("Projeto de gerenciamento de ciclo completo de transações de cartões.")
				.license(new License()
						.name("Apache 2.0")
						.url("https://springdoc.org")
						)
				).tags(Arrays.asList(new Tag().name("Solicitação").description("Solicitação de um novo cartão"),
						new Tag ().name("Limite").description("Solicitação de aumento de limite do cartão"),
						new Tag().name("Cadastro e Redefinição").description("Cadastro e redefinição de senha dos cartões"),
						new Tag().name("Histórico").description("Histórico do cartão"),
						new Tag().name("Perda e Roubo").description("Atualiza o status cartão em caso de perda ou roubo"),
						new Tag().name("Bloqueio Temporário").description("Bloqueia temporariamente o cartão"),
						new Tag().name("Ativação").description("Ativa o cartão para uso"),
						new Tag().name("Cancelamento").description("Cancelamento do cartão")));
	}
}
