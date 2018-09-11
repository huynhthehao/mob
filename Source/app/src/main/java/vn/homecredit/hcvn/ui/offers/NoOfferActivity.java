package vn.homecredit.hcvn.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleActivity;
import vn.homecredit.hcvn.ui.base.BaseViewSimpleActivity;

public class NoOfferActivity extends BaseViewSimpleActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, NoOfferActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_offer_no_offer;
    }
}
