package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.offer.OfferDetailData;
import vn.homecredit.hcvn.databinding.ActivityOfferBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;

public class OfferActivity extends BaseActivity<ActivityOfferBinding, OfferViewModel> implements OfferFragment.OnOfferListener {

    private static final String BUNDLE_OFFER = "BUNDLE_OFFER";

    @Inject
    ViewModelProvider.Factory viewModelProvider;

    public static void start(Context context, OfferModel offer) {
        Intent intent = new Intent(context, OfferActivity.class);
        intent.putExtra(BUNDLE_OFFER, Parcels.wrap(offer));
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
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
        OfferModel offer = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_OFFER));
        getViewModel().initData(offer);
        getViewModel().getModelNoVip().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == Boolean.TRUE) {
                showNoVipFragment();
            }
        });
        getViewModel().getModelExpired().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == Boolean.TRUE) {
                showExpiredFragment();
            }
        });
        getViewModel().getModelOffer().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == Boolean.TRUE) {
                showOfferFragment();
            }
        });
        getViewModel().getModelDetailOffer().observe(this, offerDetail -> {
            if (offerDetail != null ) {
                showOfferDetail(offerDetail);
            }
        });


    }

    private void showOfferDetail(OfferDetailData offerDetail) {
        OfferDetailActivity.start(this, offerDetail);
    }

    private void showNoVipFragment() {
        addFragment(new NoOfferFragment());
    }

    private void showOfferFragment() {
        OfferFragment offerFragment = OfferFragment.newInstance(getViewModel().getOffer());
        offerFragment.setOnOfferListener(this);
        addFragment(offerFragment);

    }
    private void showExpiredFragment() {
        addFragment(new ExpiredOfferFragment());

    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, fragment).commit();
    }


    @Override
    public void onDetailClicked() {
       getViewModel().getOfferFormula();
    }
}
