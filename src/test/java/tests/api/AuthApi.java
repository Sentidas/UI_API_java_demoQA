package tests.api;

import config.AuthConfig;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Cookie;
import tests.api_UI_tests.TestBase;

import java.util.Hashtable;
import java.util.Map;

import static Specs.Spec.requestSpec;
import static Specs.Spec.responseSpec;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class AuthApi extends TestBase {

    public static String userId, token, expires;
    public static AuthConfig config = ConfigFactory.create(AuthConfig.class);

    @Step("Авторизация через API")
    public static LoginResponseModel login() {
        Map<String, String> authData = new Hashtable<>();
        authData.put("userName", TestBase.login);
        authData.put("password", TestBase.password);
        return given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }

    @Step("Установить авторизационные куки в браузер")
    public static void setBrowserCookie(LoginResponseModel loginResponse) {
        token = loginResponse.getToken();
        userId = loginResponse.getUserId();
        String headerAuthorization = "Bearer " + token;

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
    }

    public static String getCookieByName(String cookie) {
        return getWebDriver().manage().getCookieNamed(cookie).getValue();
    }
}
