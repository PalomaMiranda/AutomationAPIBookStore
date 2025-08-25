Test Design – BookStore API

##  🔷 Objetivo: 

Este documento descreve o raciocínio e a justificativa para os cenários de teste automatizados implementados para a API BookStore. O objetivo é garantir que todas as funcionalidades críticas sejam validadas, incluindo fluxos positivos e tratamento de erros.

---

### 🔷 Critérios para Seleção de Cenários: 

- Cobertura de funcionalidades críticas: criação, consulta, atualização e deleção de usuários e livros.

- Validação de fluxos positivos: garantir que a API funcione como esperado.

- Validação de fluxos negativos: garantir que a API trate corretamente erros, duplicidade e dados inválidos.

- Segurança e autorização: testar geração de token e acesso protegido via Bearer Token.

- Integridade dos dados: garantir que operações de atualização e deleção não causem inconsistências.

---

### 🔷 Cenários e Justificativas: 


- Criação de usuário dinâmico: Testa o fluxo principal de criação de usuários com dados válidos, garantindo que o sistema gere IDs únicos e tokens válidos.
- Criação de usuário já existente: Garante que a API retorna o erro correto ao tentar criar duplicidade.
- Geração de token válido: Confirma que o token é gerado corretamente para usuários existentes.
- Geração de token inválido: Testa a segurança, verificando que usuários ou senhas incorretas não geram token.
- Login autorizado válido: Verifica se o token permite realizar operações protegidas.
- Login autorizado inválido: Garante que tokens inválidos não permitem acesso à API.
- Adição de livro válido: Testa se o sistema permite adicionar livros existentes a um usuário.
- Adição de livro inválido: Testa a validação da API para ISBNs inexistentes.
- Atualização de livro existente: Confirma que livros podem ser atualizados corretamente.
- Atualização de livro inexistente: Garante que tentativas inválidas são tratadas corretamente.
- Consulta de usuário válido: Verifica se os dados do usuário são retornados corretamente.
- Consulta de usuário inexistente: Garante tratamento correto de erros para UUIDs inválidos.
- Deleção de usuário existente: Testa o fluxo principal de deleção de usuários.
- Deleção com token inválido: Confirma que operações de deleção requerem autenticação válida.

---

### 🔷 Considerações Finais: 

- Os cenários positivos asseguram que funcionalidades principais da API funcionam conforme esperado.

- Os cenários negativos validam robustez e segurança, garantindo que erros sejam tratados corretamente.

- A escolha de cenários é baseada em principais operações CRUD (Create, Read, Update, Delete) e controle de acesso via token.

- O uso de usuários dinâmicos evita conflitos de dados e mantém o ambiente de teste consistente.