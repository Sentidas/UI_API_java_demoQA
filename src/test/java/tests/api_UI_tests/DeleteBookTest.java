package tests.api_UI_tests;

import junit.annotation.AddRandomBooks;
import junit.annotation.WithLogin;
import models.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

public class DeleteBookTest extends BaseTest {

    @Test
    @Tag("API_UI")
    @DisplayName("Тест на удаление всех книг из корзины пользователя")
    @WithLogin
    @AddRandomBooks(countOfBooks = 3)
    public void deleteAllBooks(UserSession session) {

        new ProfilePage().openPage()
                .deleteAllBooks()
                .checkDeletedAllBooks();
    }
}
