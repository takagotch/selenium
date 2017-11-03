package sample.geb.page;

import geb.Page

class ReserveErrorPage extends Page {
    static at = { title == "予約エラー"}
    static content = {
        message { $("#errorcheck_result") }
    }
}