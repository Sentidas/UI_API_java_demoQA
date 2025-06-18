package app.helpers;

import io.qameta.allure.Step;

import app.models.UserSession;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserCookieHelper {

    @Step("Установить авторизационные куки в браузер")
    public static void setBrowserCookie(UserSession session) {

        open("/");
        getWebDriver().manage().addCookie(new Cookie("token", session.token()));
        getWebDriver().manage().addCookie(new Cookie("userID", session.userId()));
        getWebDriver().manage().addCookie(new Cookie("expires", session.expires()));
    }

    public static String getCookieByName(String cookie) {
        return getWebDriver().manage().getCookieNamed(cookie).getValue();
    }
}
