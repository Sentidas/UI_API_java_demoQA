package tests.api_UI_tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

        public static String login = "Bravotest",
                password = "Bravotest1234@";


    @BeforeAll
    static void setup() {

    Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
    }

@AfterEach
    void shutDown() {
        closeWebDriver();
}
}
