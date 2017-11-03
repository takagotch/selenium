package sample.fluentlenium.pages;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.fluentlenium.core.FluentPage;

public class ReserveErrorPage extends FluentPage {

    @Override
    public void isAt() {
        assertThat(title(), is("予約エラー"));
    }

    public String getMessage() {
        return $("#errorcheck_result").getText();
    }
}
