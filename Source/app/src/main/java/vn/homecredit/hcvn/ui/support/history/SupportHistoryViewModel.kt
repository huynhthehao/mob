package vn.homecredit.hcvn.ui.support.history

import vn.homecredit.hcvn.ui.base.BaseViewModel
import vn.homecredit.hcvn.utils.rx.SchedulerProvider

class SupportHistoryViewModel(provider: SchedulerProvider) : BaseViewModel<Any>(provider) {

    fun getTitle():String = "Test Ok"
}