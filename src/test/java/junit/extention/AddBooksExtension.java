package junit.extention;

import junit.annotation.AddRandomBooks;
import models.BookDetailsModel;
import models.UserSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import tests.api.BookApi;

import java.util.List;

public class AddBooksExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AddBooksExtension.class);
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        // 1. Получаем сессию
        UserSession session = context.getStore(LoginExtension.NAMESPACE).get(context.getUniqueId(), UserSession.class);

        // 2. Получаем аннотацию и количество книг
        AddRandomBooks annotation = context.getRequiredTestMethod().getAnnotation(AddRandomBooks.class);
        int count = annotation.countOfBooks();

        // 3. Инициализируем BookApi и делегируем логику туда
        // Получаем список добавленных книг
        List<BookDetailsModel> addedBooks = new BookApi(session).generateRandomBooksForTestContext(count);

        // Кладем список книг в store
        context.getStore(NAMESPACE).put(context.getUniqueId(), addedBooks);
    }
}

