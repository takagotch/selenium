package sample.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReserveInputPage {
    private WebDriver driver;

    public ReserveInputPage(WebDriver driver) {
        this.driver = driver;
        if (!"予約情報入力".equals(this.driver.getTitle())) {
            throw new IllegalStateException(
                    "現在のページが間違っています: " + this.driver.getTitle());
        }
    }

    public void setReserveDate(String year, String month, String day) {
        driver.findElement(By.name("reserve_y")).clear();
        driver.findElement(By.name("reserve_y")).sendKeys(year);
        driver.findElement(By.name("reserve_m")).clear();
        driver.findElement(By.name("reserve_m")).sendKeys(month);
        driver.findElement(By.name("reserve_d")).clear();
        driver.findElement(By.name("reserve_d")).sendKeys(day);
    }

    public void setReserveTerm(String value) {
        driver.findElement(By.name("reserve_t")).clear();
        driver.findElement(By.name("reserve_t")).sendKeys(value);
    }

    public void setHeadCount(String value) {
        driver.findElement(By.name("hc")).clear();
        driver.findElement(By.name("hc")).sendKeys(value);
    }

    public void setBreakfast(boolean on) {
        if (on) {
            driver.findElement(By.id("breakfast_on")).click();
        } else {
            driver.findElement(By.id("breakfast_off")).click();
        }
    }

    public void setEarlyCheckInPlan(boolean checked) {
        if (driver.findElement(By.name("plan_a")).isSelected() != checked) {
            driver.findElement(By.name("plan_a")).click();
        }
    }

    public void setSightseeingPlan(boolean checked) {
        if (driver.findElement(By.name("plan_b")).isSelected() != checked) {
            driver.findElement(By.name("plan_b")).click();
        }
    }

    public void setGuestName(String value) {
        driver.findElement(By.name("gname")).clear();
        driver.findElement(By.name("gname")).sendKeys(value);
    }

    public ReserveConfirmPage goToNext() {
        driver.findElement(By.id("goto_next")).click();
        return new ReserveConfirmPage(driver);
    }

    public ReserveErrorPage goToNextExpectingFailure() {
        driver.findElement(By.id("goto_next")).click();
        return new ReserveErrorPage(driver);
    }
}
