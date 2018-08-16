package vn.homecredit.hcvn.ui.support.history

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import vn.homecredit.hcvn.HCVNApp
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.data.repository.SupportRepository
import vn.homecredit.hcvn.ui.base.BaseViewModel
import vn.homecredit.hcvn.utils.rx.SchedulerProvider
import javax.inject.Inject

class SupportHistoryViewModel @Inject constructor(provider: SchedulerProvider, private val supportRepo: SupportRepository) : BaseViewModel<Any>(provider) {

    val histories = MutableLiveData<MutableList<Support>>()
    val modelLoading = MutableLiveData<Boolean>()
    val title = ObservableField<String>(HCVNApp.getContext().getString(R.string.support_history_title, histories.value?.count()
            ?: 0))

    fun refreshHistories() {
        val query = supportRepo.histories
                .doAfterTerminate { modelLoading.value = false }
                .subscribe({
                    histories.value = it.data.toMutableList()
                }, {
                    handleError(it)
                })
        startSafeProcess(query)
    }
}