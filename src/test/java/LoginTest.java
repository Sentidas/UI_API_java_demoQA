import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import java.util.Hashtable;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginTest extends TestBase {

    @Test
    void loginUITest() {
        open("/login");
        $("#userName").setValue("test123456");
        $("#password").setValue("Test123456@");
        $("#login").click();
        $("#userName-value").shouldHave(text("test123456"));
    }

    @Test
    void successfulLoginWithApiTest() {
        Map<String, String> authData = new Hashtable<>();
        authData.put("userName", login);
        authData.put("password", password);

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

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $("#userName-value").shouldHave(text(login));
    }

    @Test
    void  getBooks() {
        Response responseGetBooksList = given()
                .relaxedHTTPSValidation()
                .log().all()
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .extract().response();

        String Book = responseGetBooksList.path("books[3].isbn");
        System.out.println("Book is: " );
        System.out.println("Book is " + Book);
    }
}
