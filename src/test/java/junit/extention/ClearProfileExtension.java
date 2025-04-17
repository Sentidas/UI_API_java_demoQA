package junit.extention;

import models.UserSession;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import tests.api.BookApi;

public class ClearProfileExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        UserSession session = context.getStore(LoginExtension.NAMESPACE).get(context.getUniqueId(), UserSession.class);

        new BookApi(session).deleteAllBooks();

        System.out.println("[ClearProfileExtension] afterEach вызван");
    }
}
