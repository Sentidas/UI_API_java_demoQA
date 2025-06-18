package tests.api_UI_tests;

import io.qameta.allure.*;
import junit.annotation.ClearProfileAfterTest;
import junit.annotation.EnableDynamicTestName;
import junit.annotation.WithLogin;
import models.BookDetailsModel;
import models.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookApi;
import utlis.DynamicTestNameHolder;

import java.util.List;

@Epic("Книги в корзине")
@Feature("Добавление книг")
public class AddBookTest extends BaseTest {

    @Test
    @Tag("API_UI")
    @WithLogin
  //  @ClearProfileAfterTest()
    @EnableDynamicTestName
   // @DisplayName("Добавление одной случайной книги в профиль пользователя")
    public void bookShouldAppearInProfileAfterApiAddition(UserSession session) {
        BookApi bookApi = new BookApi(session);
        BookDetailsModel book = bookApi.getRandomBook();
          DynamicTestNameHolder.set(book);
        bookApi.addSingleBookByIsbn(book.isbn());

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(
                        book.title(),
                        book.author(),
                        book.publisher());
    }

    @Test
    @WithLogin
            // @ClearProfileAfterTest()
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "swagger", url = "")
    @Tag("API_UI")
    @Tag("regress")
    @EnableDynamicTestName
    //@DisplayName("Добавление нескольких случайных книг в профиль пользователя")
    public void addBooks(UserSession session) {

        List<BookDetailsModel> addedBooks = new BookApi(session).addBooksToUserProfile(4);
        DynamicTestNameHolder.set(addedBooks);

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(addedBooks);
    }
    @Test
    @Feature("Добавление фичи через аннотацию")
    @Story("Добавление стори через аннотацию")
    @Owner("Добавление автора через аннотацию")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "swagger", url = "http")
    @DisplayName("Добавление имени теста через аннотации")
    public void testDynamicLabels() {
        Allure.getLifecycle().updateTestCase(
                t -> t.setName("Добавление имени теста через Allure")
        );
        Allure.feature("Добавление фичи через Allure");
        Allure.story("Добавление стори через аннотацию");
        Allure.label("owner", "Добавление автора через лейбл Allure");
        Allure.label("severity", SeverityLevel.CRITICAL.value());
        Allure.link("swagger", "http");
    }
}
