package vn.homecredit.hcvn.ui.support.history

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_support_history.*
import vn.homecredit.hcvn.BR
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.databinding.ActivitySupportHistoryBinding
import vn.homecredit.hcvn.ui.base.BaseActivity
import javax.inject.Inject

class SupportHistoryActivity : BaseActivity<ActivitySupportHistoryBinding, SupportHistoryViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_support_history

    override fun getViewModel(): SupportHistoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(SupportHistoryViewModel::class.java)

    override fun init() {
        super.init()
        refreshTitle(0)
    }

    private fun refreshTitle(total: Int) {
        toolbar.title = getString(R.string.support_history_title, total)
    }
}
