package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.databinding.ActivityOfferBinding;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.base.BaseSimpleActivity;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.AppUtils;

public class OfferActivity extends BaseActivity<ActivityOfferBinding, OfferViewModel> {

    private static final String BUNDLE_CAMPID = "BUNDLE_CAMPID";
    @Inject
    ViewModelProvider.Factory viewModelProvider;



    public static void start(Context context, String campId) {
        Intent intent = new Intent(context, OfferActivity.class);
        intent.putExtra(BUNDLE_CAMPID, campId);
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_offer;
    }

    @Override
    public OfferViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelProvider).get(OfferViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String campId = getIntent().getStringExtra(BUNDLE_CAMPID);
        getViewModel().setCampId(campId);
    }

}
