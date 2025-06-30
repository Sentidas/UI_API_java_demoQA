package tests.api;

import app.junit.annotation.ClearProfileAfterTest;
import app.junit.annotation.WithLogin;
import app.junit.extention.LoginMode;
import app.models.AddBookResponseModel;
import app.models.BookDetailsModel;
import app.models.UserSession;
import app.services.BookService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AddBookTest extends BaseTest {

    @Test
    @WithLogin(mode = LoginMode.API)
    @ClearProfileAfterTest()
    @DisplayName("Добавление одной случайной книги в профиль пользователя")
    public void addBook(UserSession session) {

        BookService bookService = BookService.with(session);
        BookDetailsModel book = bookService.getRandomBook();

        String expectedIsbn = book.isbn();

        AddBookResponseModel response = bookService.addSingleBookByIsbn(expectedIsbn);

        assertThat("ISBN в ответе не совпадает", response.getBooks().get(0).getIsbn(), is(expectedIsbn));

    }


    @Test
    @WithLogin(mode = LoginMode.API)
    @ClearProfileAfterTest()
    @DisplayName("Добавление нескольких случайных книг в профиль пользователя")
    public void addBooks(UserSession session) {

        BookService book = BookService.with(session);

        List<BookDetailsModel> randomBooks = book.getRandomBooksFromStore(33);

        AddBookResponseModel responseBooks = book.addBooksToUserProfileApi(randomBooks);

        List<AddBookResponseModel.Isbn> actualIsbn = responseBooks.getBooks();

        // actualIsbn.get(1).setIsbn("error");

        SoftAssertions softly = new SoftAssertions();

        for (int i = 0; i < randomBooks.size(); i++) {
            softly.assertThat(actualIsbn.get(i).getIsbn())
                    .as("ISBN ответа на позиции  " + (i + 1) + " не совпадает с переданным в body")
                    .isEqualTo(randomBooks.get(i).isbn());
        }
        softly.assertAll();
    }
}
