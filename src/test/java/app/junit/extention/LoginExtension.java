package app.junit.extention;

import app.api.LoginApi;
import app.helpers.BrowserCookieHelper;
import app.models.UserSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.services.LoginService;

public class LoginExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(LoginExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        LoginService loginService = new LoginService(new LoginApi());
       // UserSession session = loginService.login(BaseTest.login, BaseTest.password);
        UserSession session = loginService.loginDefault();

        context.getStore(NAMESPACE).put(context.getUniqueId(), session);
        BrowserCookieHelper.setBrowserCookie(session);
    }
}