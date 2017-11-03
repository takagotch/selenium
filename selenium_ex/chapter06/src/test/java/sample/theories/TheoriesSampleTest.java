package sample.theories;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import sample.pageobject.pages.ReserveErrorPage;
import sample.pageobject.pages.ReserveInputPage;

@RunWith(Theories.class)
public class TheoriesSampleTest {
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

    static class Fixture {
        Calendar reserveDate;
        String reserveTerm;
        String headCount;
        String guestName;
        String errMessage;

        Fixture(Calendar reserveDate, String reserveTerm,
                String headCount, String guestName, String errMessage) {
            this.reserveDate = reserveDate;
            this.reserveTerm  = reserveTerm;
            this.headCount = headCount;
            this.guestName = guestName;
            this.errMessage = errMessage;
        }
    }

    @DataPoints
    public static Fixture[] TEST_DATA = {
        new Fixture(null, "1", "2", "サンプルユーザ", "宿泊日が指定されていません"),
        new Fixture(nextSaturday(), "", "2", "サンプルユーザ", "泊数が指定されていません"),
        new Fixture(nextSaturday(), "1", "", "サンプルユーザ", "人数が指定されていません"),
        new Fixture(nextSaturday(), "1", "2", "", "お名前が指定されていません"),
    };

    @Theory
    public void 必須項目が空の場合にエラーになること(Fixture f) {
        driver.get("http://example.selenium.jp/reserveApp");
        ReserveInputPage inputPage = new ReserveInputPage(driver);
        if (f.reserveDate == null) {
            inputPage.setReserveDate("", "", "");
        } else {
            inputPage.setReserveDate(
                    Integer.toString(f.reserveDate.get(Calendar.YEAR)),
                    Integer.toString(f.reserveDate.get(Calendar.MONTH) + 1),
                    Integer.toString(f.reserveDate.get(Calendar.DATE)));
        }
        inputPage.setReserveTerm(f.reserveTerm);
        inputPage.setHeadCount(f.headCount);
        inputPage.setGuestName(f.guestName);
        ReserveErrorPage errorPage = inputPage.goToNextExpectingFailure();
        assertThat(errorPage.getMessage(), is(f.errMessage));
    }
}
