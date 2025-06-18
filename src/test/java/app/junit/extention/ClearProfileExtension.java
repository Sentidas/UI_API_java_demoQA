package app.junit.extention;

import app.models.UserSession;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.services.BookService;

public class ClearProfileExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        UserSession session = context.getStore(LoginExtension.NAMESPACE).get(context.getUniqueId(), UserSession.class);
        BookService.with(session).deleteAllBooks();
        System.out.println("[ClearProfileExtension] afterEach вызван");
    }
}
