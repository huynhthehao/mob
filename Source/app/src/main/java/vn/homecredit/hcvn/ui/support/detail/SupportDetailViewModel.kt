package vn.homecredit.hcvn.ui.support.detail

import android.databinding.ObservableField
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.ui.base.BaseViewModel
import vn.homecredit.hcvn.ui.support.history.SupportHistoryAdapterViewModel
import vn.homecredit.hcvn.utils.DateUtils
import vn.homecredit.hcvn.utils.DateUtils.FORMAT_SIMPLE
import javax.inject.Inject

// TODO: Refactor: Rename to SupportDetailViewModel and move package
class SupportDetailViewModel /*@Inject */constructor(data: Support) : SupportHistoryAdapterViewModel(data) {
    val statusText = ObservableField<String>(support.statusText)
}