package tests.api_UI_tests;

import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookApi;

import static com.codeborne.selenide.Selenide.sleep;

public class DeleteBookTests extends TestBase {

    @Test
    @Tag("API_UI")
    @DisplayName("Тест на удаление всех книг из корзины пользователя")
    @WithLogin
    public void deleteAllBooks() {
        BookApi book = new BookApi();
        ProfilePage profile = new ProfilePage();

        book.addBook();
        profile.openPage()
                .checkAddBook()
                .deleteAllBooks()
                .checkDeletedAllBooks();
        sleep(10000);
        System.out.println("book name set:" + BookApi.nameBook);
    }
}
