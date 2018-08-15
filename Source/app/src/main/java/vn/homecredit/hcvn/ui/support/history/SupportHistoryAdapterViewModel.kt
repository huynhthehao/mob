package vn.homecredit.hcvn.ui.support.history

import android.databinding.ObservableField
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.ui.base.BaseViewModel
import vn.homecredit.hcvn.utils.DateUtils
import vn.homecredit.hcvn.utils.DateUtils.FORMAT_SIMPLE
import vn.homecredit.hcvn.utils.rx.SchedulerProvider

class SupportHistoryAdapterViewModel(val support: Support) : BaseViewModel<Any>(null) {
    companion object {
        const val SUPPORT_DATE_SERVER_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.S"
    }

    val statusImageRes = ObservableField<Int>(when (support.status) {
        Support.Status.CLOSED -> R.drawable.ic_support_resolved
        else -> R.drawable.ic_support_pending
    })
    val title = ObservableField<String>(support.subject)
    val description = ObservableField<String>(support.description)
    val date = ObservableField<String>(support.date?.let {
        DateUtils.convertDate(it, SUPPORT_DATE_SERVER_FORMAT, FORMAT_SIMPLE)
    })

}