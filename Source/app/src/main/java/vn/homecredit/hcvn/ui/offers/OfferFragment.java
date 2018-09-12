package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityOfferBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;
import vn.homecredit.hcvn.utils.StringUtils;

public class OfferFragment extends BaseSimpleFragment {
    private static final String BUNDLE_OFFER = "BUNDLE_OFFER";
    public static OfferFragment newInstance(OfferModel offerModel) {
        OfferFragment offerFragment = new OfferFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_OFFER, Parcels.wrap(offerModel));
        offerFragment.setArguments(bundle);
        return offerFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_offer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OfferModel offerModel = Parcels.unwrap(getArguments().getParcelable(BUNDLE_OFFER)) ;
        if (offerModel == null) return;
        getView().findViewById(R.id.btnDetail).setOnClickListener(v -> {
            // TODO: 9/12/18 Start Offer Detail
            Toast.makeText(getActivity(), "clicked Detail", Toast.LENGTH_LONG).show();
        });
        updateUI(offerModel);
    }

    private void updateUI(OfferModel offerModel) {
        TextView tvFast = getView().findViewById(R.id.tvFast);
        TextView tvTimeLeft = getView().findViewById(R.id.tvTimeLeft);
        tvFast.setText(getContext().getString(R.string.offers_cash, StringUtils.getCurrencyMaskValue(offerModel.getMaxLoanAmount())));
        tvTimeLeft.setText(getContext().getString(R.string.time_left, offerModel.getDayLeft()));

    }
}
