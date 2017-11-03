package sample.findby.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReserveConfirmPage {
    private WebDriver driver;
    @FindBy(id = "price")
    private WebElement price;
    @FindBy(id = "commit")
    private WebElement commitButton;

    public ReserveConfirmPage(WebDriver driver) {
        this.driver = driver;
        if (!"予約内容確認".equals(this.driver.getTitle())) {
            throw new IllegalStateException(
                    "現在のページが間違っています: " + this.driver.getTitle());
        }
    }

    public String getPrice() {
        return price.getText();
    }

    public void commit() {
        commitButton.click();
    }
}
