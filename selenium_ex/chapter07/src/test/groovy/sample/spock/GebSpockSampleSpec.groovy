package sample.spock

import geb.spock.GebSpec
import sample.geb.page.ReserveConfirmPage
import sample.geb.page.ReserveErrorPage
import sample.geb.page.ReserveInputPage

class GebSpockSampleSpec extends GebSpec {
    
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

    def "宿泊予約が成功すること"() {
        given: "予約情報入力ページに対し"
        to ReserveInputPage
        
        when: "次の土曜日の日付と"
        Calendar nextSaturday = nextSaturday()
        setReserveDate(nextSaturday.get(Calendar.YEAR),
                nextSaturday.get(Calendar.MONTH) + 1,
                nextSaturday.get(Calendar.DATE))
        
        and: "その他適当な値を入力し"
        reserveTerm = "1"
        headCount = "2"
        breakfast = "on"
        earlyCheckInPlan = true
        guestName = "サンプルユーザ"
        
        and: "次のページへ進むと"
        goNextButton.click()

        then: "予約内容確認ページが表示され"
        at ReserveConfirmPage
        
        and: "正しい宿泊料金が表示され"
        price.text() == "21500"
        
        and: "確定ボタンが押せる"
        commitButton.click()
    }
    
    def "入力に誤りがある場合にエラーになること"() {
        given: "予約情報入力ページに対し"
        to ReserveInputPage

        when: "過去の日付と"
        setReserveDate("1999", "1", "1")
        
        and: "その他適当な値を入力し"
        guestName = "テストユーザ"
        
        and: "次のページへ進むと"
        goNextButton.click()
        
        then: "予約エラーページが表示され"
        at ReserveErrorPage

        and: "過去日付エラーが表示される"
        message.text() == "宿泊日には、翌日以降の日付を指定してください。"
    }
}