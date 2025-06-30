package app.junit.annotation;

import app.junit.extention.LoginExtension;
import app.junit.extention.LoginMode;
import app.junit.extention.UserSessionResolver;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@ExtendWith({LoginExtension.class, UserSessionResolver.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface WithLogin {
    LoginMode mode() default LoginMode.UI;
}