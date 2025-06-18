package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.BookDetailsModel;
import tests.api_UI_tests.BaseTest;

import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class ProfilePage extends BasePage {

    private final SelenideElement userName = $("#userName-value"),
            buttonDeleteAllBooks = $(byText("Delete All Books")),
            modalDelete = $("#closeSmallModal-ok"),
            buttonDeleteOneBook = $("#delete-record-undefined"),
            buttonNext = $(".-next"),
            bookList = $(".ReactTable"),
            buttonDeleteBook = $(" .action-buttons #delete-record-undefined"),
            buttonOkForDeleteBook = $("#closeSmallModal-ok");


    private final ElementsCollection bookRows = $$(".rt-tr-group");

    @Step("Открытие страницы профиля пользователя")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Проверка имени пользователя в профиле")
    public ProfilePage checkNameUserInProfile() {
        userName.shouldHave(text(BaseTest.login));
        return this;
    }

    @Step("Удаление книги")
    public ProfilePage deleteAllBooks() {
        buttonDeleteAllBooks.scrollTo().click();
        modalDelete.click();
        switchTo().alert().accept();
        return this;
    }

    @Step("Удаление книги")
    public ProfilePage deleteBooks(List<BookDetailsModel> addedBooks, int countOfBooksForDelete) {
        for (BookDetailsModel book : addedBooks) {
            String titleBook = book.title();
            bookRows.findBy(text(titleBook)).find("#delete-record-undefined").click();
            $("#example-modal-sizes-title-sm").shouldBe(exist);
            buttonOkForDeleteBook.click();

        }
        return this;
    }

    @Step("Проверка, что все книги удалены из корзины")
    public void checkDeletedAllBooks() {
        bookList.shouldHave(text("No rows found"));
    }

    @Step("Проверка, что книга добавлена")
    public ProfilePage checkBookWasAddedToProfile(String... bookDetails) {

        for (String detail : bookDetails) {
            bookList.shouldHave(text(detail));
        }
        return this;
    }

    @Step("Проверка, что книги добавлена")
    public ProfilePage checkBookWasAddedToProfile(List<BookDetailsModel> books) {

        int pageSize = 5;

        for (int i = 0; i < books.size(); i += pageSize) {
            List<BookDetailsModel> subList = books.subList(i, Math.min(i + pageSize, books.size()));


            for (BookDetailsModel book : subList) {
                bookRows.findBy(text(book.title()))
                        .shouldHave(text(book.author()))
                        .shouldHave(text(book.publisher()));
            }

            if (i + pageSize < books.size()) {
                next();
            }
        }
        return this;
    }

    @Step("Переход на следующую страницу")
    public void next() {
        removeBanners();
        buttonNext.click();

        // Явное ожидание: пока исчезнет "Loading..." индикатор
        // $(".-loading-inner").shouldBe(hidden);

    }
}



