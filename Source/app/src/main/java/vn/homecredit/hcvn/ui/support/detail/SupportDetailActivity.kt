package vn.homecredit.hcvn.ui.support.detail

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import kotlinx.android.synthetic.main.activity_support_detail.*
import org.parceler.Parcels
import vn.homecredit.hcvn.BR
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.databinding.ActivitySupportDetailBinding
import vn.homecredit.hcvn.ui.base.BaseActivity

class SupportDetailActivity : View.OnClickListener,
        BaseActivity<ActivitySupportDetailBinding, SupportDetailViewModel>() {
    lateinit var support: Support

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_support_detail

    override fun getViewModel() = SupportDetailViewModel(support)

    override fun onCreate(savedInstanceState: Bundle?) {
        support = Parcels.unwrap(intent.getParcelableExtra<Parcelable>(PARAM_SUPPORT_DETAIL_OBJECT))
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        super.init()
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }

    companion object {
        const val PARAM_SUPPORT_DETAIL_OBJECT = "PARAM_SUPPORT_DETAIL_OBJECT"
    }
}
