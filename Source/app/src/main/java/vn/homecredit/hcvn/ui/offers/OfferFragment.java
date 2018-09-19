package vn.homecredit.hcvn.ui.offers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;
import vn.homecredit.hcvn.utils.StringUtils;

public class OfferFragment extends BaseSimpleFragment {
    private static final String BUNDLE_OFFER = "BUNDLE_OFFER";
    private OnOfferListener onOfferListener;
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
            if (onOfferListener != null) {
                onOfferListener.onDetailClicked();
            }
        });
        updateUI(offerModel);
    }

    public void setOnOfferListener(OnOfferListener onOfferListener) {
        this.onOfferListener = onOfferListener;
    }

    private void updateUI(OfferModel offerModel) {
        TextView tvFast = getView().findViewById(R.id.tvFast);
        TextView tvTimeLeft = getView().findViewById(R.id.tvTimeLeft);
        tvFast.setText(getContext().getString(R.string.offers_cash, StringUtils.getCurrencyMaskValue(offerModel.getMaxLoanAmount())));
        tvTimeLeft.setText(getContext().getString(R.string.time_left, offerModel.getDayLeft()));
    }

    public interface OnOfferListener {
        void onDetailClicked();
    }
}
