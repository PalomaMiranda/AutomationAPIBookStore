BookStore API Automation

Automação de testes da API BookStore utilizando Java, TestNG, Cucumber e Rest Assured. O projeto realiza testes de criação, consulta, atualização e deleção de usuários e livros.


🔷 Tecnologias e Dependências

- Java 17+;

- Gradle para gerenciamento de dependências;

- Cucumber para BDD e integração com TestNG;

- TestNG como framework de testes;

- Rest Assured para testes de API REST.


🔷 Configuração e Execução

1. Clone o Repositório: 
- git clone <URL_DO_REPOSITORIO>
- cd bookstore-api

2. Execute os testes via Gradle:
- ./gradlew clean cucumber

🔷 Dependências principais:

- testImplementation("io.cucumber:cucumber-java:7.18.0")
- testImplementation("io.cucumber:cucumber-testng:7.18.0")
- testImplementation("org.testng:testng:7.8.0")
- testImplementation("io.rest-assured:rest-assured:5.5.0")
- implementation("org.slf4j:slf4j-simple:2.0.12")


🔷 Estrutura do Projeto

Bookstore

├─ src/test/java/
│               ├─ bookstore/          
│               ├─ runner/              
│               └─ steps/              
│
├─ src/test/resources/
│               ├─ db/         
│               └─ features/                 
│
├─ build.gradle             
└─ README.md


🔷 Funcionalidades Testadas

Account: 

- Criação de usuário dinâmico;

- Tentativa de criação de usuário já existente;

- Geração de token válido;

- Geração de token inválido;

- Autorização de login válido;

- Autorização de login inválido;

- Consulta de usuário por UUID;

- Consulta de usuário inexistente;

- Deleção de usuário existente;

- Deleção com token inválido.

Books: 

- Adição de livro ao usuário dinâmico;

- Adição de livro inválido (ISBN não disponível);

- Atualização de livro existente pelo ISBN;

- Atualização de livro inexistente (ISBN inválido). 


🔷 Estrutura dos Testes

- Cucumber Features: arquivos .feature descrevem os cenários em linguagem natural (Gherkin).

- Step Definitions: classes Java que implementam a lógica dos testes chamando a API via Rest Assured.

- BookStore: contém todos os métodos que realizam requisições HTTP (GET, POST, PUT, DELETE).

- Runner: executa todos os testes do Cucumber com TestNG e gera relatório HTML.