package tests.api_UI_tests;

import junit.annotation.ClearProfileAfterTest;
import junit.annotation.WithLogin;
import models.BookDetailsModel;
import models.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookApi;

import java.util.List;

public class AddBookTest extends BaseTest {

    @Test
    @Tag("API_UI")
    @WithLogin
    @ClearProfileAfterTest()
    @DisplayName("Тест на добавление одной случайной книги в профиль пользователя")
    public void addBook(UserSession session) {
        BookApi bookApi = new BookApi(session);
        BookDetailsModel book = bookApi.getRandomBook();

        bookApi.addSingleBookByIsbn(book.isbn());

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(
                        book.title(),
                        book.author(),
                        book.publisher());
    }

    @Test
    @Tag("API_UI")
    @WithLogin
    @ClearProfileAfterTest()
    @DisplayName("Тест на добавление нескольких случайных книг в профиль пользователя")
    public void addBooks(UserSession session) {

        List<BookDetailsModel> addedBooks = new BookApi(session).addBooksToUserProfile(6);

        new ProfilePage().openPage()
                .checkBookWasAddedToProfile(addedBooks);
    }
}
