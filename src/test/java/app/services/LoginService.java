package app.services;

import app.api.LoginApi;
import app.config.TestCredentials;
import io.qameta.allure.Step;
import app.models.UserSession;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginService {

   private final LoginApi loginApi;

    public LoginService(LoginApi loginApi) {
        this.loginApi = loginApi;
    }

    @Step("Авторизация через API")
    public UserSession login(String username, String password) {
        Map<String, String> authData = new HashMap<>();
        authData.put("userName", username);
        authData.put("password", password);

        return loginApi.login(authData);
    }

    @Step("Авторизация через API дефолтным пользователем")
    public UserSession loginDefault() {
        return login(TestCredentials.USERNAME, TestCredentials.PASSWORD);
    }
}
