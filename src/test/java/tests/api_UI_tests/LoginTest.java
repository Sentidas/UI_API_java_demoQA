package tests.api_UI_tests;

import io.restassured.response.Response;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import java.util.Hashtable;
import java.util.Map;

import static Specs.Spec.requestSpec;
import static Specs.Spec.responseSpec;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

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
        authData.put("userName", TestBase.login);
        authData.put("password", TestBase.password);

        LoginResponseModel authResponse = given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        open("/profile");
        $("#userName-value").shouldHave(text(TestBase.login));
    }

    @Test
    void  getBooks() {
        Response responseGetBooksList = given(requestSpec)
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().response();

        String Book = responseGetBooksList.path("books[3].isbn");
        System.out.println("Book is: " );
        System.out.println("Book is " + Book);
    }
}
