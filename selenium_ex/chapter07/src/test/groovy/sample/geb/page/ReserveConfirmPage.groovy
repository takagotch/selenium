package sample.geb.page

import geb.Page

class ReserveConfirmPage extends Page {
    static at = { title == "予約内容確認"}
    static content = {
        price { $("#price") }
        commitButton { $("#commit") }
    }
}