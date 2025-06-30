package tests.api;

import app.junit.annotation.ClearProfileAfterTest;
import app.junit.annotation.WithLogin;
import app.junit.extention.LoginMode;
import app.models.AddBookErrorResponseModel;
import app.models.AddBookResponseModel;
import app.models.BookDetailsModel;
import app.models.UserSession;
import app.services.BookService;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Epic("API: Книги в профиле")
@Feature("API: Негативные сценарии добавления книг в профиль")
public class AddBookErrorTest extends BaseTest {


    @Test
    @WithLogin(mode = LoginMode.API)
    @DisplayName("Добавление книги в профиль пользователя без авторизационных данных")
    public void addBookWithoutLogin(UserSession session) {

        BookService bookService = BookService.with(session);
        BookDetailsModel book = bookService.getRandomBook();

        String expectedIsbn = book.isbn();

        AddBookErrorResponseModel response = bookService.addSingleBookByIsbnWithoutLogin(expectedIsbn);

        assertThat(response.message(), is("User not authorized!"));

    }


    @Test
    @WithLogin(mode = LoginMode.API)
    @ClearProfileAfterTest()
    @DisplayName("Повторное добавление книги в профиль пользователя")
    public void repeatAddBook(UserSession session) {

        BookService bookService = BookService.with(session);

        BookDetailsModel book = bookService.getRandomBook();

        String expectedIsbn = book.isbn();

        AddBookResponseModel firstResponse = bookService.addSingleBookByIsbn(expectedIsbn);

        assertThat("Первое добавление ISBN в ответе не совпадает",
                firstResponse.getBooks().get(0).getIsbn(),
                is(expectedIsbn));

        Response secondResponse = bookService.repeatAddBookByIsbn(expectedIsbn);


        assertThat("Повторное добавление должно вернуть 400",
                secondResponse.statusCode(), is(400));

        assertThat("Сообщение об ошибке должно быть ожидаемым",
                secondResponse.jsonPath().get("message"),
                is("ISBN already present in the User's Collection!"));

    }

    @Test
    @WithLogin(mode = LoginMode.API)
    @DisplayName("Отправка пустого списка книг в теле запроса")
    public void addZeroBook(UserSession session) {

        BookService book = BookService.with(session);

        AddBookErrorResponseModel responseBooks = book.addEmptyBookListToProfile();

        assertThat(responseBooks.message(), is("Collection of books required."));

    }

    @Test
    @WithLogin(mode = LoginMode.API)
    @DisplayName("Добавление несуществующей книги в профиль пользователя")
    public void addMoreBooks(UserSession session) {

        BookService bookService = BookService.with(session);

        BookDetailsModel book = bookService.getRandomBook();

        String expectedIsbn = book.isbn();

        AddBookErrorResponseModel response = bookService.addSingleBookByIsbnWithError(expectedIsbn + "555");

        assertThat(response.message(), is("ISBN supplied is not available in Books Collection!"));

    }
}
