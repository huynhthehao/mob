package vn.homecredit.hcvn.ui.offers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.AppUtils;

public class NoOfferFragment extends BaseSimpleFragment {

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    protected boolean isSupportInjection() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_no_offer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }


    private void updateUI() {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData == null) return;
        String phoneSupport = versionRespData.getCustomerSupportPhone();
        if (phoneSupport == null) {
            phoneSupport = AppConstants.SUPPORT_PHONE;
        }
        TextView tvPhoneSupport = getView().findViewById(R.id.tvPhoneSupport);
        tvPhoneSupport.setText(phoneSupport);
        tvPhoneSupport.setOnClickListener(v -> openPhoneSupport(tvPhoneSupport.getText().toString()));

    }

    private void openPhoneSupport(String text) {
        AppUtils.openDeviceCallDialog(getActivity(), text);
    }

}
