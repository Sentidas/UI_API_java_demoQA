package pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasePage {

    public void removeBanners() {
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('#fixedban').remove();");
    }
}
