package junit.annotation;

import junit.extention.LoginExtension;
import junit.extention.UserSessionResolver;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


    @ExtendWith({LoginExtension.class, UserSessionResolver.class})
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)

    public @interface WithLogin {
    }