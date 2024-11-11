package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byText;
import io.qameta.allure.Step;
import tests.api_UI_tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage extends BasePage {

    private final SelenideElement userName = $("#userName-value");

    @Step("Открытие страницы профиля пользователя")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Проверка имени пользователя в профиле")
    public ProfilePage checkNameUserInProfile() {
        userName.shouldHave(text(TestBase.login));
        return this;
    }

    @Step("Удаление книги")
    public void deleteAllBooks() {
        $(byText("Delete All Books")).scrollTo().click();
      //  $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        switchTo().alert().accept();
    }
}



