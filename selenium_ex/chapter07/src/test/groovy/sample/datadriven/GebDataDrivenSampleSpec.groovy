package sample.datadriven

import geb.spock.GebSpec
import sample.geb.page.ReserveErrorPage
import sample.geb.page.ReserveInputPage
import spock.lang.Unroll

class GebDataDrivenSampleSpec extends GebSpec {

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

    @Unroll
    def "必須項目が空の場合にエラー「#errMessage」になること"() {
        given: "予約情報入力ページに対し"
        to ReserveInputPage

        when: "特定の必須項目を空にして"
        if (rDate == null) {
            setReserveDate("", "", "")
        } else {
            setReserveDate(rDate.get(Calendar.YEAR),
                    rDate.get(Calendar.MONTH) + 1,
                    rDate.get(Calendar.DATE));
        }
        reserveTerm = rTerm
        headCount = hCount
        guestName = gName
        
        and: "次のページへ進むと"
        goNextButton.click()
        
        then: "予約エラーページが表示され"
        at ReserveErrorPage

        and: "必須項目エラーが表示される"
        message.text() == errMessage;
        
        where:
        rDate|rTerm|hCount|gName||errMessage
        null|"1"|"2"|"サンプルユーザ"||"宿泊日が指定されていません"
        nextSaturday()|""|"2"|"サンプルユーザ"||"泊数が指定されていません"
        nextSaturday()|"1"|""|"サンプルユーザ"||"人数が指定されていません"
        nextSaturday()|"1"|"2"|""||"お名前が指定されていません"
    }
}