package tests.api_UI_tests;

import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookApi;

public class AddBookTests {

    @Test
    @Tag("API_UI")
    @WithLogin
    @DisplayName("Тест на добавление одной книги в корзину пользователя")
    public void addBook() {
        BookApi book = new BookApi();
        ProfilePage profile = new ProfilePage();

        book.addBook();
        profile.openPage()
                .checkAddBook();

    }
}
