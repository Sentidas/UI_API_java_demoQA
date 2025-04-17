package tests.apiTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.api.LoginApi;

import java.util.Hashtable;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class DeleteBookApiTests extends TestBase {
    @Test
    void negative400DeleteBookApiTest() {


        Map<String, String> authData = new Hashtable<>();
        authData.put("userName", TestBase.login);
        authData.put("password", TestBase.password);
        String isbn = "9781449325862";

        Response authResponse = given()
                .relaxedHTTPSValidation()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://demoqa.com/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        String token = authResponse.path("token");
        String userId = authResponse.path("userId");
        String headerAuthorization = "Bearer " + token;
        Map<String, String> addBookData = new Hashtable<>();
        addBookData.put("userId", userId);
        addBookData.put("isbn", isbn);

        Response responseDeleteBook = given()
                .relaxedHTTPSValidation()
                .log().all()
                .contentType(JSON)
                .header("Authorization", headerAuthorization)
                .body(addBookData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("code", is("1206"))
                .body("message", is("ISBN supplied is not available in User's Collection!"))
                .extract().response();
    }
}
