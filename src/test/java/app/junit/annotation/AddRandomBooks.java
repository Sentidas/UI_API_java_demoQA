package app.junit.annotation;

import app.junit.extention.AddBooksExtension;
import app.junit.extention.BooksResolver;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({AddBooksExtension.class, BooksResolver.class})
public @interface AddRandomBooks {
    int countOfBooks() default -1;
}
