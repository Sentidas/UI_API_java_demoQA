package junit.extention;

import helpers.BrawserCookieHelper;
import models.UserSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import tests.api.LoginApi;

public class LoginExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(LoginExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        UserSession session = LoginApi.login();
        context.getStore(NAMESPACE).put(context.getUniqueId(), session);
        BrawserCookieHelper.setBrowserCookie(session);
    }
}