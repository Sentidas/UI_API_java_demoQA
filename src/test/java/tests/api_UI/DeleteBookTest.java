package tests.api_UI;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import app.junit.annotation.AddRandomBooks;
import app.junit.annotation.WithLogin;
import app.models.BookDetailsModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import java.util.List;

@Epic("Книги в корзине")
@Feature("Удаление книг")
public class DeleteBookTest extends BaseTest {

    @Test
    @Tag("API_UI")
    @DisplayName("Удаление всех книг из корзины пользователя")
    @WithLogin
    @AddRandomBooks()
    public void deleteAllBooks() {

        new ProfilePage().openPage()
                .deleteAllBooks()
                .checkDeletedAllBooks();
    }

    @Test
    @Tag("API_UI")
    @DisplayName("Удаление нескольких книг из корзины пользователя")
    @WithLogin
    @Disabled("Need review")
    @AddRandomBooks(countOfBooks = 3)
    public void deleteBook(List<BookDetailsModel> addedBooks) throws InterruptedException {

        ProfilePage page = new ProfilePage();

        page.openPage();
        page.deleteBooks(addedBooks, 3)
                .checkDeletedAllBooks();
    }
}
