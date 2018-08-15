package vn.homecredit.hcvn.ui.support.history

import android.arch.lifecycle.MutableLiveData
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.ui.base.BaseViewModel
import vn.homecredit.hcvn.utils.rx.SchedulerProvider

class SupportHistoryViewModel(provider: SchedulerProvider) : BaseViewModel<Any>(provider) {

    var histories = MutableLiveData<List<Support>>()
    fun getTitle():String = "Test Ok"
}