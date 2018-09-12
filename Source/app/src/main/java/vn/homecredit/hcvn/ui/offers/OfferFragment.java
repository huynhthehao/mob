package vn.homecredit.hcvn.ui.offers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;
import vn.homecredit.hcvn.utils.StringUtils;

public class OfferFragment extends BaseSimpleFragment {
    private static final String BUNDLE_OFFER = "BUNDLE_OFFER";
    private OnOfferListenner onOfferListenner;
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
            if (onOfferListenner != null) {
                onOfferListenner.onDetailClicked();
            }
        });
        updateUI(offerModel);
    }

    public void setOnOfferListenner(OnOfferListenner onOfferListenner) {
        this.onOfferListenner = onOfferListenner;
    }

    private void updateUI(OfferModel offerModel) {
        TextView tvFast = getView().findViewById(R.id.tvFast);
        TextView tvTimeLeft = getView().findViewById(R.id.tvTimeLeft);
        tvFast.setText(getContext().getString(R.string.offers_cash, StringUtils.getCurrencyMaskValue(offerModel.getMaxLoanAmount())));
        tvTimeLeft.setText(getContext().getString(R.string.time_left, offerModel.getDayLeft()));
    }

    public interface OnOfferListenner {
        void onDetailClicked();
    }
}
