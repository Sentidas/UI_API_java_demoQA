package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byText;
import io.qameta.allure.Step;
import tests.api.BookApi;
import tests.api_UI_tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static tests.api.BookApi.idBook;
import static tests.api.BookApi.nameBook;

public class ProfilePage extends BasePage {

    private final SelenideElement userName = $("#userName-value"),
    bookList =  $(".ReactTable"),
    buttonDeleteAllBooks =  $(byText("Delete All Books")),
    modalDelete = $("#closeSmallModal-ok"),
    buttonDeleteOneBook = $("#delete-record-undefined");

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
    public ProfilePage deleteAllBooks() {
       buttonDeleteAllBooks.scrollTo().click();
       modalDelete.click();
        switchTo().alert().accept();
        return this;
    }

    @Step("Проверка, что все книги удалены из корзины")
    public void checkDeletedAllBooks() {
        bookList.shouldHave(text("No rows found"));
    }

    @Step("Проверка, что книга добавлена")
    public ProfilePage checkAddBook() {
        BookApi book = new BookApi();
        book.getNameBookWithId(idBook);
        bookList.shouldHave(text(nameBook));
        return this;
    }
}



