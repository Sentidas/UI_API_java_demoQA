package tests.api_UI;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import app.junit.annotation.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Epic("Авторизация")
@Feature("Авторизация через UI")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Успешная авторизация через UI")
    void loginUITest() {
        open("/login");
        $("#userName").setValue("test123456");
        $("#password").setValue("Test123456@");
        $("#login").click();
        $("#userName-value").shouldHave(text("test123456"));
    }

    @Test
    @WithLogin
    @Tag("API_UI")
    @DisplayName("Успешная авторизация через API и проверка имени пользователя в профиле через UI")
    void successfulLoginWithApiTest() {
        String username = "test123456";

        new ProfilePage().openPage()
                .checkNameUserInProfile(username);
    }
}
