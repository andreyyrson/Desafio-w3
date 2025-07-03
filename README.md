# 🚀 Como Executar
Pré-requisitos

Java 17 ou superior
Maven 3.6+
Docker (opcional, para banco de dados)

Executando a Aplicação

Clone o repositório

bashgit clone <url-do-repositorio>
cd feature-flags-spring-boot

Configure o banco de dados (opcional)

bash# Para usar com Docker
docker-compose up -d

Execute a aplicação

bash# Com Maven
mvn spring-boot:run

# Ou compile e execute o JAR
mvn clean package
java -jar target/feature-flags-*.jar

Acesse a aplicação


API: http://localhost:8080
Health Check: http://localhost:8080/actuator/health

Perfis de Execução
bash# Desenvolvimento
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Produção
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Testes
mvn spring-boot:run -Dspring-boot.run.profiles=test
# 📁 Estrutura do Projeto
├── src/
│   ├── main/java/          # Código fonte
│   ├── main/resources/     # Configurações
│   └── test/              # Testes
├── docs/                  # 📚 Documentação completa
├── docker-compose.yml     # Configuração Docker
└── pom.xml               # Dependências Maven
# 📚 Documentação
Toda a documentação técnica, arquitetura, APIs e guias de uso estão disponíveis na pasta docs/.
# 🧪 Executando Testes
bash# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=FeatureFlagControllerTest

# Com cobertura
mvn test jacoco:report
# 🔧 Configuração
As configurações principais estão em:

src/main/resources/application.yml
src/main/resources/application-{profile}.yml

# 📞 Suporte
Para dúvidas técnicas, consulte a documentação na pasta docs/ ou abra uma issue.

## ⭐ Dica: Confira a pasta docs/ para guias detalhados sobre como usar as feature flags!
