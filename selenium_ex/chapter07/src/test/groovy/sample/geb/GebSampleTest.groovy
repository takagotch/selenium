package sample.geb

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*
import geb.junit4.GebTest
import org.junit.Test
import sample.geb.page.ReserveConfirmPage
import sample.geb.page.ReserveErrorPage
import sample.geb.page.ReserveInputPage

class GebSampleTest extends GebTest {

    private static Calendar nextSaturday() {
        Calendar calendar = Calendar.getInstance()
        int weekday = calendar.get(Calendar.DAY_OF_WEEK)
        if (weekday == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 7)
        } else {
            calendar.add(Calendar.DATE, Calendar.SATURDAY - weekday)
        }
        return calendar
    }

    @Test
    void 宿泊予約が成功すること() {
        // 予約情報入力ページ
        to ReserveInputPage
        Calendar nextSaturday = nextSaturday()
        setReserveDate(nextSaturday.get(Calendar.YEAR),
                nextSaturday.get(Calendar.MONTH) + 1,
                nextSaturday.get(Calendar.DATE))
        reserveTerm = "1"
        headCount = "2"
        breakfast = "on"
        earlyCheckInPlan = true
        guestName = "サンプルユーザ"
        goNextButton.click()

        // 予約内容確認ページ
        at ReserveConfirmPage
        assertThat(price.text(), is("21500"))
        commitButton.click()
    }
    
    @Test
    void 入力に誤りがある場合にエラーになること() {
        to ReserveInputPage
        setReserveDate("1999", "1", "1")
        guestName = "テストユーザ"
        goNextButton.click()
        
        at ReserveErrorPage
        assertThat(message.text(),
                is("宿泊日には、翌日以降の日付を指定してください。"))
    }
}