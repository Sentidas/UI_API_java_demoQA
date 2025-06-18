package app.junit.extention;

import io.qameta.allure.Allure;
import app.models.BookDetailsModel;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.utlis.DynamicTestNameHolder;

import java.util.List;
import java.util.stream.Collectors;

public class DynamicTestNameExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Object data = DynamicTestNameHolder.get();

        if (data instanceof BookDetailsModel book) {
            Allure.getLifecycle().updateTestCase(tc ->
                    tc.setName("Добавлена книга: " + book.title()));
        } else if (data instanceof List<?> list &&
                !list.isEmpty() &&
                list.get(0) instanceof BookDetailsModel) {

            // Сборка названий книг
            String titles = list.stream()
                    .map(obj -> ((BookDetailsModel) obj).title())
                    .collect(Collectors.joining(", "));

            Allure.getLifecycle().updateTestCase(tc ->
                    tc.setName("Добавлены книги: " + titles));
        }

        DynamicTestNameHolder.clear();
    }
}