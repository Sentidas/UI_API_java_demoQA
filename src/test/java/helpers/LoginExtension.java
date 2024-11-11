package helpers;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import tests.api.AuthApi;

import static com.codeborne.selenide.Selenide.open;

public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        AuthApi.setBrowserCookie(AuthApi.login());
    }
}