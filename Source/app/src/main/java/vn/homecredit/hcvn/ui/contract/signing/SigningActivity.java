package vn.homecredit.hcvn.ui.contract.signing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.parceler.Parcel;
import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ActivityContractSigningBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.contract.main.ContractViewModel;
import vn.homecredit.hcvn.utils.Log;

public class SigningActivity extends Activity{

    private static final String BUNDLE_CONTRACT = "BUNDLE_CONTRACT";
    private HcContract hcContract;

    public static void start(Context context, HcContract hcContract) {
        Intent intent = new Intent(context, SigningActivity.class);
        intent.putExtra(BUNDLE_CONTRACT, Parcels.wrap(hcContract));
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(BUNDLE_CONTRACT)) {
            hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT));
            Log.debug(hcContract.getClientName());
        }
    }
}
