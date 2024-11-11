package tests.api_UI_tests;

import helpers.WithLogin;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookApi;

public class DeleteBookTests extends TestBase {

    @Test
    @WithLogin
    public void deleteAllBooks() {
        BookApi book = new BookApi();
        ProfilePage profile = new ProfilePage();

        book.addBook();
        profile.openPage()
                .deleteAllBooks();
    }
}
