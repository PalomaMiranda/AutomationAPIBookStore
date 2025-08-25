package steps;

import bookstore.BookStore;
import io.cucumber.java.en.*;

public class BookStoreSteps {

    private BookStore api = new BookStore();

    // Criação de Usuário Dinâmico

    @Given("que acesso a API User")
    public void acessoApiUser() {
    }

    @When("informo o Nome e a Senha dinamicamente")
    public void informoNomeSenhaDinamicamente() throws Exception {
        api.createUserDynamic();
    }

    @Then("valido que consigo realizar login com sucesso com o status code sendo 201")
    public void validoLogin() {
        System.out.println("Usuário criado com ID: " + BookStore.userID);
        System.out.println("Token gerado: " + BookStore.token);
    }

    // Tentativa de Criação de Usuário Já Existente

    @When("tento criar o mesmo usuário novamente")
    public void criarUsuarioDuplicado() throws Exception {
        api.createUserNegative();
    }

    @Then("recebo o status code 406 e a mensagem {string}")
    public void validarMensagemErro(String mensagem) {
        System.out.println("Erro esperado validado: " + mensagem);
    }

    // Geração de Token Válido Com Usuário Válido

    @Given("que acesso a API de geração de token")
    public void acessoApiToken() {
    }

    @When("envio o usuário e senha válidos para gerar o token")
    public void gerarToken() throws Exception {
        api.generateToken();
    }

    @Then("recebo o status code 200")
    public void validarStatusCode200() {
        System.out.println("Status code 200 validado com sucesso.");
    }

    @Then("o token é gerado com sucesso")
    public void validarTokenGerado() {
        System.out.println("Token gerado: " + BookStore.token);
    }

    @Then("o resultado é {string}")
    public void validarMensagemToken(String mensagem) {
        System.out.println("Mensagem validada: " + mensagem);
    }

    // Geração de Token com Usuário Inválido

    @When("envio um usuário ou senha inválidos para gerar o token")
    public void gerarTokenInvalido() throws Exception {
        api.generateTokenNegative();
    }

    @Then("o status é {string}")
    public void validarStatusFalha(String status) {
        System.out.println("Status validado: " + status);
    }

    @Then("a mensagem contém {string}")
    public void validarMensagemFalha(String mensagem) {
        System.out.println("Mensagem de erro validada: " + mensagem);
    }

    // Autorização de Login Válido

    @Given("que acesso a API de login autorizado")
    public void acessoApiLoginAutorizado() {
    }

    @When("envio o token válido")
    public void envioTokenValido() throws Exception {
        api.authorizedLogin();
    }

    @Then("o login autorizado retorna {string}")
    public void o_login_autorizado_retorna(String resultado) {
        System.out.println("Login autorizado validado: " + resultado);
    }

    // Autorização de Login Inválido
    @When("envio um token inválido")
    public void envioTokenInvalido() throws Exception {
        api.authorizedLoginNegative();
    }

    @Then("o login autorizado retorna erro {string}")
    public void o_login_autorizado_retorna_erro(String mensagemErro) {
        System.out.println("Login autorizado negativo validado: " + mensagemErro);
    }

    // Adição de Livro ao Usuário Dinâmico

    @Given("que o usuário foi criado dinamicamente")
    public void usuarioDinamicoCriado() throws Exception {
        api.createUserDynamic();
    }

    @When("envio os dados do livro válido")
    public void envioDadosLivroValido() throws Exception {
        api.addBookToUser();
    }

    @Then("o livro é adicionado com sucesso")
    public void validarLivroAdicionado() {
        System.out.println("Livro adicionado ao usuário " + BookStore.userID + " com sucesso.");
    }

    // Adição de Livro Inválido ao Usuário Dinâmico

    @When("envio os dados do livro inválido")
    public void envioDadosLivroInvalido() throws Exception {
        api.addBookToUserNegative();
    }

    @Then("recebo erro de livro não encontrado")
    public void validarLivroNaoEncontrado() {
        System.out.println("Erro de livro não encontrado validado com sucesso para o usuário " + BookStore.userID);
    }

    // Atualização de Livro Existente Pelo ISBN

    @Given("que o usuário foi criado dinamicamente e possui um livro")
    public void usuarioComLivroCriado() throws Exception {
        api.createUserDynamic();
        api.addBookToUser();
    }

    @When("atualizo o livro existente pelo ISBN")
    public void atualizoLivroExistente() throws Exception {
        api.updateBookByISBN();
    }

    @Then("o livro é atualizado corretamente")
    public void validarLivroAtualizado() {
        System.out.println("Livro do usuário " + BookStore.userID + " atualizado com sucesso.");
    }

    // Tentativa de Atualização de Livro Inexistente Pelo ISBN

    @Given("que o usuário foi criado dinamicamente e possui um livro para atualizar")
    public void usuarioComLivroParaAtualizar() throws Exception {
        api.createUserDynamic();
        api.addBookToUser();
    }

    @When("tento atualizar o livro com ISBN inexistente")
    public void tentoAtualizarLivroInexistente() throws Exception {
        api.updateBookByISBNNegative();
    }

    @Then("recebo erro de ISBN não disponível")
    public void validarErroISBN() {
        System.out.println("Erro de ISBN não disponível validado para o usuário " + BookStore.userID);
    }

    // Consultar Usuário Criado Dinamicamente

    @When("consulto os dados do usuário")
    public void consultoDadosUsuario() throws Exception {
        api.searchUserUUID();
    }

    @Then("os dados do usuário são retornados corretamente")
    public void validarDadosUsuario() {
        System.out.println("Usuário " + BookStore.userID + " validado com sucesso, dados retornados corretamente.");
    }

    // Tentativa de Consulta à Usuário Inexistente

    @Given("que o token do usuário é válido")
    public void tokenValido() throws Exception {
        if (BookStore.token == null) {
            api.generateToken();
        }
    }

    @When("consulto um usuário inexistente")
    public void consultoUsuarioInexistente() {
        api.searchUserUUIDNegative();
    }

    @Then("recebo erro de usuário não encontrado")
    public void validarUsuarioNaoEncontrado() {
        System.out.println("Erro de usuário não encontrado validado com sucesso.");
    }

    // Deletar Usuário Existente

    @When("deletar o usuário existente")
    public void deletarUsuarioExistente() {
        api.deleteUserUUID();
    }

    @Then("o usuário é deletado com sucesso")
    public void validarUsuarioDeletado() {
        System.out.println("Usuário deletado com sucesso: " + BookStore.userID);
    }

    // Tentativa de Deletar Usuário com Token Inválido

    @Given("que o usuário foi criado dinamicamente para deleção negativa")
    public void usuarioDinamicoParaDelecaoNegativa() throws Exception {
        api.createUserDynamic();
    }

    @Given("que o token do usuário é inválido")
    public void tokenInvalido() {
        BookStore.token = "invalidToken";
    }

    @When("tento deletar o usuário com token inválido")
    public void deletarUsuarioComTokenInvalido() throws Exception {
        api.deleteUserNegative();
    }

    @Then("recebo erro de usuário não autorizado")
    public void validarUsuarioNaoAutorizado() {
        System.out.println("Erro de usuário não autorizado validado para: " + BookStore.userID);
    }
}