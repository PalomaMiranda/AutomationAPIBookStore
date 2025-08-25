@Tag

Feature: Testes da API BookStore

  Scenario: Criação de Usuário Dinâmico
    Given que acesso a API User
    When informo o Nome e a Senha dinamicamente
    Then valido que consigo realizar login com sucesso com o status code sendo 201

  Scenario: Tentativa de Criação de Usuário Já Existente
    Given que acesso a API User
    When tento criar o mesmo usuário novamente
    Then recebo o status code 406 e a mensagem "User exists!"

  Scenario: Geração de Token Válido Com Usuário Válido
    Given que acesso a API de geração de token
    When envio o usuário e senha válidos para gerar o token
    Then recebo o status code 200
    And o token é gerado com sucesso
    And o resultado é "User authorized successfully."

  Scenario: Geração de Token com Usuário Inválido
    Given que acesso a API de geração de token
    When envio um usuário ou senha inválidos para gerar o token
    Then recebo o status code 200
    And o status é "Failed"
    And a mensagem contém "User authorization failed"

  Scenario: Autorização de Login Válido
    Given que acesso a API de login autorizado
    When envio o token válido
    Then o login autorizado retorna "true"

  Scenario: Autorização de Login Inválido
    Given que acesso a API de login autorizado
    When envio um token inválido
    Then o login autorizado retorna erro "User not found!"

  Scenario: Adição de Livro ao Usuário Dinâmico
    Given que o usuário foi criado dinamicamente
    When envio os dados do livro válido
    Then o livro é adicionado com sucesso

  Scenario: Adição de Livro Inválido ao Usuário Dinâmico
    Given que o usuário foi criado dinamicamente
    When envio os dados do livro inválido
    Then recebo erro de livro não encontrado

  Scenario: Atualização de Livro Existente Pelo ISBN
    Given que o usuário foi criado dinamicamente e possui um livro
    When atualizo o livro existente pelo ISBN
    Then o livro é atualizado corretamente

  Scenario: Tentativa de Atualização de Livro Inexistente Pelo ISBN
    Given que o usuário foi criado dinamicamente e possui um livro para atualizar
    When tento atualizar o livro com ISBN inexistente
    Then recebo erro de ISBN não disponível

  Scenario: Consultar Usuário Criado Dinamicamente
    Given que o usuário foi criado dinamicamente
    When consulto os dados do usuário
    Then os dados do usuário são retornados corretamente

  Scenario: Tentativa de Consulta à Usuário Inexistente
    Given que o token do usuário é válido
    When consulto um usuário inexistente
    Then recebo erro de usuário não encontrado

  Scenario: Deletar Usuário Existente
    Given que o token do usuário é válido
    When deletar o usuário existente
    Then o usuário é deletado com sucesso

  Scenario: Tentativa de Deletar Usuário com Token Inválido
    Given que o usuário foi criado dinamicamente para deleção negativa
    And que o token do usuário é inválido
    When tento deletar o usuário com token inválido
    Then recebo erro de usuário não autorizado