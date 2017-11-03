package sample.findby;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import sample.pageobject.pages.ReserveConfirmPage;
import sample.pageobject.pages.ReserveErrorPage;
import sample.pageobject.pages.ReserveInputPage;

public class FindBySampleTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private static Calendar nextSaturday() {
        Calendar calendar = Calendar.getInstance();
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 7);
        } else {
            calendar.add(Calendar.DATE, Calendar.SATURDAY - weekday);
        }
        return calendar;
    }

    @Test
    public void 宿泊予約が成功すること() {
        // 予約情報入力ページ
        driver.get("http://example.selenium.jp/reserveApp");
        ReserveInputPage inputPage
        = PageFactory.initElements(driver, ReserveInputPage.class);
        Calendar nextSaturday = nextSaturday();
        inputPage.setReserveDate(
                Integer.toString(nextSaturday.get(Calendar.YEAR)),
                Integer.toString(nextSaturday.get(Calendar.MONTH) + 1),
                Integer.toString(nextSaturday.get(Calendar.DATE)));
        inputPage.setReserveTerm("1");
        inputPage.setHeadCount("2");
        inputPage.setBreakfast(true);
        inputPage.setEarlyCheckInPlan(true);
        inputPage.setGuestName("サンプルユーザ");

        // 予約内容確認ページ
        ReserveConfirmPage confirmPage = inputPage.goToNext();
        assertThat(confirmPage.getPrice(), is("21500"));
        confirmPage.commit();
    }

    @Test
    public void 入力に誤りがある場合にエラーになること() {
        driver.get("http://example.selenium.jp/reserveApp");
        ReserveInputPage inputPage
        = PageFactory.initElements(driver, ReserveInputPage.class);
        inputPage.setReserveDate("1999", "1", "1");
        inputPage.setGuestName("テストユーザ");
        ReserveErrorPage errorPage = inputPage.goToNextExpectingFailure();
        assertThat(errorPage.getMessage(),
                is("宿泊日には、翌日以降の日付を指定してください。"));
    }
}
