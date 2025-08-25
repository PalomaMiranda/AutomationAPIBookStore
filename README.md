📚 BookStore API Automation

Automação de testes da API BookStore utilizando Java, TestNG, Cucumber e Rest Assured.
O projeto cobre cenários de criação, consulta, atualização e deleção de usuários e livros, garantindo a qualidade da API.

🔷 Tecnologias e Dependências

- Java 17+;

- Gradle para gerenciamento de dependências;

- Cucumber para BDD e integração com TestNG;

- TestNG como framework de testes;

- Rest Assured para testes de API REST;

- Extent Reports para relatórios HTML interativos.


🔷 Configuração e Execução

1. Clone o Repositório: 
- git clone https://github.com/PalomaMiranda/AutomationAPIBookStore
- cd AutomationAPIBookStore

2. Execute os testes via Gradle:
- ./gradlew clean cucumber

3. No Windows (PowerShell):
- gradlew.bat clean cucumber


🔷 Relatórios de Teste (Extent Reports)

Ao final da execução, um relatório interativo em HTML é gerado:

📂 Caminho:

build/reports/extent-report/extent.html

Para visualizar, basta abrir no navegador:
./build/reports/extent-report/extent.html

O relatório mostra:

- Nome dos cenários executados;

- Status (Pass/Fail/Skipped);

- Logs detalhados de cada etapa;

- Captura de mensagens e exceções.

O relatório também é publicação como artefato no GitHub Actions.

É possível baixar o relatório diretamente da aba Actions → Job → Artifacts.


🔷 Integração Contínua (CI) – GitHub Actions

Os testes estão integrados ao GitHub Actions, rodando em:

- Push para as branches main ou master;

- Pull Requests;

- Execução manual (workflow_dispatch);

- Agendamento semanal: toda quinta-feira às 12:00 UTC (09:00 BRT), sendo escolhido por ser um dia comumente usado para deploys.

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
├─ build.gradle.kts           
├─ README.md
└─ TestDesign.md

🔷 Funcionalidades Testadas

👤 Account:

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

📚 Books: 

- Adição de livro ao usuário dinâmico;

- Adição de livro inválido (ISBN não disponível);

- Atualização de livro existente pelo ISBN;

- Atualização de livro inexistente (ISBN inválido). 


🔷 Estrutura dos Testes

- Cucumber Features: arquivos .feature descrevem os cenários em linguagem natural (Gherkin).

- Step Definitions: classes Java que implementam a lógica dos testes chamando a API via Rest Assured.

- BookStore: contém todos os métodos que realizam requisições HTTP (GET, POST, PUT, DELETE).

- Runner: executa todos os testes do Cucumber com TestNG e gera relatório HTML.