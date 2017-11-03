package sample.findby.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReserveErrorPage {
    private WebDriver driver;
    @FindBy(id = "errorcheck_result")
    private WebElement errorMessage;

    public ReserveErrorPage(WebDriver driver) {
        this.driver = driver;
        if (!"予約エラー".equals(this.driver.getTitle())) {
            throw new IllegalStateException(
                    "現在のページが間違っています: " + this.driver.getTitle());
        }
    }

    public String getMessage() {
        return errorMessage.getText();
    }
}
