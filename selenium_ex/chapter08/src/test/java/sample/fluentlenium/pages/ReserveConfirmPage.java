package sample.fluentlenium.pages;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.fluentlenium.core.FluentPage;

public class ReserveConfirmPage extends FluentPage {
 
    @Override
    public void isAt() {
        assertThat(title(), is("予約内容確認"));
    }

    public String getPrice() {
        return $("#price").getText();
    }

    public void commit() {
        $("#commit").click();
    }
}
