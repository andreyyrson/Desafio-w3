# 🚀 Como Executar
Pré-requisitos

Java 17 ou superior
Maven 3.6+

Executando a Aplicação

Clone o repositório

bashgit clone <url-do-repositorio>
cd feature-flags-spring-boot

Configure o banco de dados (opcional)

# Com Maven
mvn spring-boot:run

Acesse a aplicação


API: http://localhost:8080

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
mvn test
# 🔧 Configuração
As configurações principais estão em:

src/main/resources/application.yml
