package vn.homecredit.hcvn.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseSimpleActivity;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.AppUtils;

public class NoOfferActivity extends BaseSimpleActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoOfferActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isSupportInjection() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_no_offer);
        enableNavigationClicked();
        updateUI();
    }

    private void updateUI() {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData == null) return;
        String phoneSupport = versionRespData.getCustomerSupportPhone();
        if (phoneSupport == null) {
            phoneSupport = AppConstants.SUPPORT_PHONE;
        }
        TextView tvPhoneSupport = findViewById(R.id.tvPhoneSupport);
        tvPhoneSupport.setText(phoneSupport);
        tvPhoneSupport.setOnClickListener(v -> openPhoneSupport(tvPhoneSupport.getText().toString()));

    }

    private void openPhoneSupport(String text) {
        AppUtils.openDeviceCallDialog(this, text);
    }

}
