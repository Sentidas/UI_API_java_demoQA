package app.junit.extention;

import app.api.BookApi;
import app.junit.annotation.AddRandomBooks;
import app.models.BookDetailsModel;
import app.models.UserSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.services.BookService;

import java.util.List;

public class AddBooksExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AddBooksExtension.class);
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        // Получаем сессию
        UserSession session = context.getStore(LoginExtension.NAMESPACE).get(context.getUniqueId(), UserSession.class);

        // Получаем аннотацию и количество книг
        AddRandomBooks annotation = context.getRequiredTestMethod().getAnnotation(AddRandomBooks.class);
        int count = annotation.countOfBooks();

        // Инициализируем BookApi и делегируем логику туда
        // Получаем список добавленных книг

        BookService service = BookService.with(session);
        List<BookDetailsModel> addedBooks = service.generateRandomBooksForTestContext(count);
        // List<BookDetailsModel> addedBooks = new BookService(session).generateRandomBooksForTestContext(count);

        // Кладем список книг в store
        context.getStore(NAMESPACE).put(context.getUniqueId(), addedBooks);
    }
}

