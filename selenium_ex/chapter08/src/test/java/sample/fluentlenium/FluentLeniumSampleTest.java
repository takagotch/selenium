package sample.fluentlenium;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import sample.fluentlenium.pages.ReserveConfirmPage;
import sample.fluentlenium.pages.ReserveErrorPage;
import sample.fluentlenium.pages.ReserveInputPage;

public class FluentLeniumSampleTest extends FluentTest {
    
    @Override
    public String getDefaultBaseUrl() {
        return "http://example.selenium.jp";
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
        ReserveInputPage inputPage = createPage(ReserveInputPage.class);
        goTo(inputPage);
        inputPage.isAt();
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
        confirmPage.isAt();
        assertThat(confirmPage.getPrice(), is("21500"));
        confirmPage.commit();
    }

    @Test
    public void 入力に誤りがある場合にエラーになること() {
        ReserveInputPage inputPage = createPage(ReserveInputPage.class);
        goTo(inputPage);
        inputPage.isAt();
        inputPage.setReserveDate("1999", "1", "1");
        inputPage.setGuestName("テストユーザ");
        ReserveErrorPage errorPage = inputPage.goToNextExpectingFailure();
        errorPage.isAt();
        assertThat(errorPage.getMessage(),
                is("宿泊日には、翌日以降の日付を指定してください。"));
    }
}
