package sample.fluentlenium.pages;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.fluentlenium.core.FluentPage;

public class ReserveInputPage extends FluentPage {

    @Override
    public String getUrl() {
        return "/reserveApp";
    }
    
    @Override
    public void isAt() {
        assertThat(title(), is("予約情報入力"));
    }

    public void setReserveDate(String year, String month, String day) {
        $(withName("reserve_y")).text(year);
        $(withName("reserve_m")).text(month);
        $(withName("reserve_d")).text(day);
    }

    public void setReserveTerm(String value) {
        $(withName("reserve_t")).text(value);
    }

    public void setHeadCount(String value) {
        $(withName("hc")).text(value);
    }

    public void setBreakfast(boolean on) {
        if (on) {
            $("#breakfast_on").click();
        } else {
            $("#breakfast_off").click();
        }
    }

    public void setEarlyCheckInPlan(boolean checked) {
        if ($(0, withName("plan_a")).isSelected() != checked) {
            $(withName("plan_a")).click();
        }
    }

    public void setSightseeingPlan(boolean checked) {
        if ($(0, withName("plan_b")).isSelected() != checked) {
            $(withName("plan_b")).click();
        }
    }

    public void setGuestName(String value) {
        $(withName("gname")).text(value);
    }

    public ReserveConfirmPage goToNext() {
        $("#goto_next").click();
        return createPage(ReserveConfirmPage.class);
    }

    public ReserveErrorPage goToNextExpectingFailure() {
        $("#goto_next").click();
        return createPage(ReserveErrorPage.class);
    }
}
