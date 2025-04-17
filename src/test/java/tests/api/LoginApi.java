package tests.api;

import io.qameta.allure.Step;
import models.UserSession;
import tests.api_UI_tests.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static specs.Spec.requestSpec;
import static specs.Spec.responseSpec;

public class LoginApi extends BaseTest {


    @Step("Авторизация через API")
    public static UserSession login() {
        Map<String, String> authData = new HashMap<>();
        authData.put("userName", BaseTest.login);
        authData.put("password", BaseTest.password);

        return given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(UserSession.class);
    }
}
