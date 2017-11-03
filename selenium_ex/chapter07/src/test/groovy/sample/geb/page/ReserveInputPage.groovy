package sample.geb.page

import geb.Page

class ReserveInputPage extends Page {
    static url = "http://example.selenium.jp/reserveApp"
    static at = { title == "予約情報入力"}
    static content = {
        reserveYear { $(name: "reserve_y") }
        reserveMonth { $(name: "reserve_m") }
        reserveDay { $(name: "reserve_d") }
        reserveTerm { $(name: "reserve_t") }
        headCount { $(name: "hc") }
        breakfast { $(name: "bf") }
        earlyCheckInPlan { $(name: "plan_a") }
        sightseeingPlan { $(name: "plan_b") }
        guestName { $(name: "gname") }
        goNextButton(to: [ReserveConfirmPage, ReserveErrorPage]) { $("#goto_next") } 
    }

    void setReserveDate(year, month, day) {
        reserveYear = year
        reserveMonth = month
        reserveDay = day
    }
}
