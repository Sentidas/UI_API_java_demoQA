package app.junit.extention;

import app.api.LoginApi;
import app.helpers.BrowserCookieHelper;
import app.junit.annotation.WithLogin;
import app.models.UserSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.services.LoginService;

import java.util.Optional;

public class LoginExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(LoginExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        LoginService loginService = new LoginService(new LoginApi());
        UserSession session = loginService.loginDefault();

        context.getStore(NAMESPACE).put(context.getUniqueId(), session);

        LoginMode mode = context.getElement()
                .flatMap(el -> Optional.ofNullable(el.getAnnotation(WithLogin.class)))
                .map(annotation -> annotation.mode())
                .orElse(LoginMode.UI);

        if (mode == LoginMode.UI) {
            BrowserCookieHelper.setBrowserCookie(session);
        }
    }
}
