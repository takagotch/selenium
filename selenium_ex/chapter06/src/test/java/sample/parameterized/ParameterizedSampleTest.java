package sample.parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import sample.pageobject.pages.ReserveErrorPage;
import sample.pageobject.pages.ReserveInputPage;

@RunWith(Parameterized.class)
public class ParameterizedSampleTest {
    private WebDriver driver;
    private Calendar reserveDate;
    private String reserveTerm;
    private String headCount;
    private String guestName;
    private String errMessage;

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

    @Parameters(name = "メッセージ:{4}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {null, "1", "2", "サンプルユーザ", "宿泊日が指定されていません"},
                {nextSaturday(), "", "2", "サンプルユーザ", "泊数が指定されていません"},
                {nextSaturday(), "1", "", "サンプルユーザ", "人数が指定されていません"},
                {nextSaturday(), "1", "2", "", "お名前が指定されていません"},
        });
    }

    public ParameterizedSampleTest(Calendar reserveDate, String reserveTerm,
            String headCount, String guestName, String errMessage) {
        this.reserveDate = reserveDate;
        this.reserveTerm = reserveTerm;
        this.headCount = headCount;
        this.guestName = guestName;
        this.errMessage = errMessage;
    }

    @Test
    public void 必須項目が空の場合にエラーになること() {
        driver.get("http://example.selenium.jp/reserveApp");
        ReserveInputPage inputPage = new ReserveInputPage(driver);
        if (reserveDate == null) {
            inputPage.setReserveDate("", "", "");
        } else {
            inputPage.setReserveDate(
                    Integer.toString(reserveDate.get(Calendar.YEAR)),
                    Integer.toString(reserveDate.get(Calendar.MONTH) + 1),
                    Integer.toString(reserveDate.get(Calendar.DATE)));
        }
        inputPage.setReserveTerm(reserveTerm);
        inputPage.setHeadCount(headCount);
        inputPage.setGuestName(guestName);
        ReserveErrorPage errorPage = inputPage.goToNextExpectingFailure();
        assertThat(errorPage.getMessage(), is(errMessage));
    }
}
