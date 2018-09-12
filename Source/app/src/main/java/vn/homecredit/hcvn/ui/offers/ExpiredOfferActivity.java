package vn.homecredit.hcvn.ui.offers;

import android.content.Context;
import android.content.Intent;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseViewSimpleActivity;

public class ExpiredOfferActivity extends BaseViewSimpleActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ExpiredOfferActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expired_offer;
    }
}
