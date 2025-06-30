package tests.api_UI;

import app.junit.annotation.ClearProfileAfterTest;
import app.junit.annotation.EnableDynamicTestName;
import app.junit.annotation.WithLogin;
import app.junit.extention.LoginMode;
import app.models.BookDetailsModel;
import app.models.UserSession;
import app.services.BookService;
import app.utlis.DynamicTestNameHolder;
import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import java.util.List;

@Epic("Книги в профиле")
@Feature("Добавление книг")
public class AddBookTest extends BaseTest {

    @Test
    @Tag("API_UI")
    @WithLogin(mode = LoginMode.UI)
    @ClearProfileAfterTest()
    @EnableDynamicTestName
    public void bookShouldAppearInUserProfileAfterApiAddition(UserSession session) {
        BookService bookService = BookService.with(session);
        BookDetailsModel book = bookService.getRandomBook();
        DynamicTestNameHolder.set(book);

        bookService.addSingleBookByIsbn(book.isbn());

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(
                        book.title(),
                        book.author(),
                        book.publisher());
    }

    @Test
    @WithLogin
    @ClearProfileAfterTest()
    @Severity(SeverityLevel.CRITICAL)
    @Tag("API_UI")
    @Tag("regress")
    @EnableDynamicTestName
    public void booksShouldAppearInUserProfileAfterApiAddition(UserSession session) {

        List<BookDetailsModel> addedBooks = BookService.with(session)
                .addBooksToUserProfile(4);

        DynamicTestNameHolder.set(addedBooks);

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(addedBooks);
    }
}
