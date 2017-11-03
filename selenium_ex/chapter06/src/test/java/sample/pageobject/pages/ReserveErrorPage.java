package sample.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReserveErrorPage {
    private WebDriver driver;

    public ReserveErrorPage(WebDriver driver) {
        this.driver = driver;
        if (!"予約エラー".equals(this.driver.getTitle())) {
            throw new IllegalStateException(
                    "現在のページが間違っています: " + this.driver.getTitle());
        }
    }

    public String getMessage() {
        return driver.findElement(By.id("errorcheck_result")).getText();
    }
}
