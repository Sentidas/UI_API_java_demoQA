package tests.api_UI_tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    public static String login = "test123456",
            password = "Test123456@";

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.timeout = 8000;
    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }
}
