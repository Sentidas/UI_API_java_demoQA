package app.services;

import app.api.LoginApi;
import app.config.AuthConfig;
import io.qameta.allure.Step;
import app.models.UserSession;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginService {

   private final LoginApi loginApi;
   private final AuthConfig auth = ConfigFactory.create(AuthConfig.class);

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
        return login(auth.username(), auth.password());
    }

//    @Step("Авторизация через API дефолтным пользователем")
//    public UserSession loginDefault() {
//        return login(TestCredentials.USERNAME, TestCredentials.PASSWORD);
//    }
}
