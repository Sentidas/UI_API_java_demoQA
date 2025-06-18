package app.junit.annotation;

import app.junit.extention.DynamicTestNameExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(DynamicTestNameExtension.class)
public @interface EnableDynamicTestName {
}
