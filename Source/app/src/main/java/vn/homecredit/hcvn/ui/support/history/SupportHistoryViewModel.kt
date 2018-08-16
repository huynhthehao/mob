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

class SupportHistoryViewModel @Inject constructor(provider: SchedulerProvider,
                                                  private val repo: SupportRepository)
    : BaseViewModel<Any>(provider) {

    val histories = MutableLiveData<List<Support>>()
    val modelLoading = MutableLiveData<Boolean>()
    val title = ObservableField<String>(getTitle(histories.value?.count() ?: 0))

    private fun getTitle(total: Int) = HCVNApp.getContext().getString(R.string.support_history_title, total)

    fun refreshHistories() {
//        val query = supportRepo.histories
        val query = repo.histories
                .doAfterTerminate { modelLoading.value = false }
                .subscribe({
                    histories.value = it.data
                    title.set(getTitle(it.data.count()))
                }, {
                    handleError(it)
                })
        startSafeProcess(query)
    }
}