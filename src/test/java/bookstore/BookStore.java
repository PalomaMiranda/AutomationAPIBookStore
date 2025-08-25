package bookstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookStore {
    String baseUrl = "https://bookstore.toolsqa.com/";

    public static String userID;
    public static String token;

    public String readJson(String jsonPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(jsonPath)));
    }

    public void createUserDynamic() throws IOException {
        String uri = baseUrl + "Account/v1/User";

        String uniqueUsername = "User_" + System.currentTimeMillis();

        String jsonBodyTemplate = readJson("src/test/resources/db/account.json");

        String jsonBody = jsonBodyTemplate.replace("{{username}}", uniqueUsername);

        userID =
                given()
                        .contentType("application/json")
                        .body(jsonBody)

                .when()
                        .post(uri)

                .then()
                        .statusCode(201)
                        .extract()
                        .path("userID");

        token =
                given()
                        .contentType("application/json")
                        .body(jsonBody)

                .when()
                        .post(baseUrl + "Account/v1/GenerateToken")

                .then()
                        .statusCode(200)
                        .extract()
                        .path("token");
    }

    public void createUserNegative() throws IOException {
        String uri = baseUrl + "Account/v1/User";

        String jsonBody = readJson("src/test/resources/db/account.json");

        given()
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(406)
                .body("message", equalTo("User exists!"));
    }

    public void generateToken() throws IOException {
        String uri = baseUrl + "Account/v1/GenerateToken";

        String jsonBody = readJson("src/test/resources/db/account.json");

        token =
        given()
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("expires", notNullValue())
                .body("status", equalTo("Success"))
                .body("result", equalTo("User authorized successfully."))
                .extract()
                    .path("token");
    }

    public void generateTokenNegative() throws IOException {
        String uri = baseUrl + "Account/v1/GenerateToken";

        String jsonBody = "{ \"userName\": \"56247\", \"password\": \"wrongPass\" }";

        given()
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .body("status", equalTo("Failed"))
                .body("result", containsString("User authorization failed"));
    }

    public void authorizedLogin() throws IOException {
        String uri = baseUrl + "Account/v1/Authorized";

        String jsonBody = readJson("src/test/resources/db/account.json");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(200)
                .body(equalTo("true"));
    }

    public void authorizedLoginNegative() throws IOException {
        String uri = baseUrl + "Account/v1/Authorized";

        String jsonBody = "{ \"userName\": \"56247\", \"password\": \"wrongPass\" }";

        given()
                .header("Authorization", "Bearer invalidToken")
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(404)
                .body("message", equalTo("User not found!"))
                .body("code", notNullValue());
    }

    public void addBookToUser() throws IOException {
        String uri = baseUrl + "BookStore/v1/Books";

        String jsonBody = readJson("src/test/resources/db/books.json");

        jsonBody = jsonBody.replace("{{userId}}", userID)
                .replace("{{isbn}}", "9781449325862");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(201)
                .body("books[0].isbn", equalTo("9781449325862"));
    }

    public void addBookToUserNegative() throws IOException {
        String uri = baseUrl + "BookStore/v1/Books";

        String jsonBody = readJson("src/test/resources/db/books.json");

        jsonBody = jsonBody.replace("{{userId}}", userID)
                .replace("{{isbn}}", "0000000000000");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(jsonBody)

        .when()
                .post(uri)

        .then()
                .statusCode(400)
                .body("code", notNullValue())
                .body("message", containsString("ISBN supplied is not available"));
    }

    public void updateBookByISBN() throws IOException {
        String uri = baseUrl + "BookStore/v1/Books/{ISBN}";

        String oldIsbn = "9781449325862";
        String newIsbn = "9781449331818";

        String jsonBody = readJson("src/test/resources/db/booksUpdate.json");

        jsonBody = jsonBody.replace("{{userId}}", userID)
                .replace("{{isbn}}", newIsbn);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .pathParam("ISBN", oldIsbn)
                .body(jsonBody)

        .when()
                .put(uri)

        .then()
                .statusCode(200)
                .body("books[0].isbn", equalTo(newIsbn))
                .body("userId", equalTo(userID));
    }

    public void updateBookByISBNNegative() throws IOException {
        String uri = baseUrl + "BookStore/v1/Books/{ISBN}";

        String wrongIsbn = "0000000000000";

        String jsonBody = readJson("src/test/resources/db/booksUpdate.json");

        jsonBody = jsonBody.replace("{{userId}}", userID)
                .replace("{{isbn}}", wrongIsbn);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .pathParam("ISBN", wrongIsbn)
                .body(jsonBody)

        .when()
                .put(uri)

        .then()
                .statusCode(400)
                .body("code", notNullValue())
                .body("message", containsString("ISBN supplied is not available"));
    }

    public void searchUserUUID() throws IOException {
        String uri = baseUrl + "Account/v1/User/{UUID}";

        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("UUID", userID)

        .when()
                .get(uri)

        .then()
                .statusCode(200)
                .body("userId", equalTo(userID))
                .body("username", notNullValue())
                .body("books", notNullValue());
    }

    public void searchUserUUIDNegative() {
        String uri = baseUrl + "Account/v1/User/{UUID}";

        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("UUID", "0000-0000-0000-0000")

        .when()
                .get(uri)

        .then()
                .statusCode(401)
                .body("code", notNullValue())
                .body("message", equalTo("User not found!"));
    }

    public void deleteUserUUID() {
        String uri = baseUrl + "Account/v1/User/{UUID}";

        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("UUID", userID)

        .when()
                .delete(uri)

        .then()
                .statusCode(204);
    }

    public void deleteUserNegative() {
        String uri = baseUrl + "Account/v1/User/{UUID}";

        given()
                .header("Authorization", "Bearer invalidToken")
                .contentType("application/json")
                .pathParam("UUID", userID)

        .when()
                .delete(uri)

        .then()
                .statusCode(401)
                .body("code", notNullValue())
                .body("message", equalTo("User not authorized!"));
    }
}