package vn.homecredit.hcvn.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleActivity;

public class NoOfferActivity extends BaseSimpleActivity{

    public static void start(Context context) {
        Intent intent = new Intent(context, NoOfferActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_no_offer);
    }
}
