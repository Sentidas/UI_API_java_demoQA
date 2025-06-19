package tests.api_UI;

import app.config.WebDriverConfig;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {


    @BeforeAll
    static void setup() {
        WebDriverConfig config = ConfigFactory.create(
                WebDriverConfig.class,
                System.getProperties()
        );

        Configuration.baseUrl = config.getBaseUrl();
        Configuration.browser = config.getBrowser().toLowerCase();
        Configuration.pageLoadStrategy = "eager";
        RestAssured.baseURI = config.getBaseUrl();


    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }
}
