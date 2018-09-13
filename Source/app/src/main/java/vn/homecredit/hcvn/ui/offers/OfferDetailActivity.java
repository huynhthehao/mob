package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.text.NumberFormat;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.offer.OfferDetailData;
import vn.homecredit.hcvn.databinding.ActivityOfferDetailBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.utils.AppUtils;
import vn.homecredit.hcvn.utils.CommonUtils;

public class OfferDetailActivity extends BaseActivity<ActivityOfferDetailBinding, OfferDetailViewModel> {
    public static final String PARAM_OFFER_DETAIL_OBJECT = "PARAM_OFFER_DETAIL_OBJECT";

    @Inject
    ViewModelProvider.Factory providerFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_offer_detail;
    }

    @Override
    public OfferDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, providerFactory).get(OfferDetailViewModel.class);
    }

    @Override
    protected void init() {
        super.init();
        getViewDataBinding().setCallCenterClick(ignored -> AppUtils.openDeviceCallDialog(this, getViewModel().getTallNumber().get()));

        getViewDataBinding().toolbar.setNavigationOnClickListener(ignored -> onBackPressed());

        getViewDataBinding().loanSeekBar.setOnValueChangeListener((seekBar, value) -> {
            NumberFormat nf = CommonUtils.getDefaultNumberFormmater();
            Integer realValue = value * 1000000;
            seekBar.setValueTitle(getString(R.string.currency, nf.format(realValue)));
            getViewModel().getLoanAmount().set(realValue);
        });

        String detailInString = getIntent().getStringExtra(PARAM_OFFER_DETAIL_OBJECT);
        getViewModel().getOfferDetail().set(new Gson().fromJson(detailInString, OfferDetailData.class));
    }

    public static void start(Context context, OfferDetailData offerDetail) {
        Intent app = new Intent(context, OfferDetailActivity.class);
        app.putExtra(PARAM_OFFER_DETAIL_OBJECT, new Gson().toJson(offerDetail));
        context.startActivity(app);
    }
}
