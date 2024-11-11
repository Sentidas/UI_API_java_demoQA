package tests.api_UI_tests;

import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends TestBase {

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
    @DisplayName("Успешная авторизация через API и проверка имени пользователя в профиле через  UI")
    void successfulLoginWithApiTest() {
        ProfilePage  profile = new ProfilePage();
        profile.openPage()
                .checkNameUserInProfile();
    }
}
