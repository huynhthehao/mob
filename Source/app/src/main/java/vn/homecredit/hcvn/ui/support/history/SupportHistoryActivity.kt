package vn.homecredit.hcvn.ui.support.history

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_support_history.*
import org.parceler.Parcels
import vn.homecredit.hcvn.BR
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.databinding.ActivitySupportHistoryBinding
import vn.homecredit.hcvn.ui.base.BaseActivity
import vn.homecredit.hcvn.ui.base.BaseRecyclerAdapter
import vn.homecredit.hcvn.ui.support.detail.SupportDetailActivity
import javax.inject.Inject

class SupportHistoryActivity : BaseActivity<ActivitySupportHistoryBinding, SupportHistoryViewModel>(),
        BaseRecyclerAdapter.OnItemClickListener<Support>,
        SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_support_history

    override fun getViewModel() = ViewModelProviders.of(this, viewModelFactory).get(SupportHistoryViewModel::class.java)

    override fun init() {
        super.init()
        viewModel.histories.observe(this, Observer {
            refreshHistories.isRefreshing = false
            it?.let {
                rvHistories.adapter = SupportHistoryAdapter(it, this)
            }
        })
        viewModel.modelLoading.observe(this, Observer {
            refreshHistories.isRefreshing = it ?: false
        })
        refreshHistories.setOnRefreshListener(this)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        onRefresh()
    }

    override fun onItemClicked(item: Support) {
        val detailIntent = Intent(this, SupportDetailActivity::class.java)
        detailIntent.putExtra(SupportDetailActivity.PARAM_SUPPORT_DETAIL_OBJECT, Parcels.wrap(item))
        startActivity(detailIntent)
    }

    override fun onRefresh() {
        viewModel.refreshHistories()
    }
}
