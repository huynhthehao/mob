/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/2/18 8:30 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcCreditCard;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ActivityCreditcardDetailBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.contract.statement.StatementsActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;

public class CreditCardDetailActivity extends BaseActivity<ActivityCreditcardDetailBinding, CreditCardDetailViewModel> implements CreditCardDetailListener {

    public static final String BUNDLE_INPUT_CREDIT_CARD = "INPUT_CREDIT_CARD ";

    @Inject
    CreditCardDetailViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_creditcard_detail;
    }

    @Override
    public CreditCardDetailViewModel getViewModel() {
        return viewModel;
    }

    public static Intent getNewIntent(Context context, HcCreditCard card) {
        Intent newInstance = new Intent(context, CreditCardDetailActivity.class);
        if (card != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_INPUT_CREDIT_CARD, Parcels.wrap(card));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent().hasExtra(BUNDLE_INPUT_CREDIT_CARD)) {
            HcCreditCard card = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_INPUT_CREDIT_CARD));
            viewModel.setListener(this);
            viewModel.setData(card);
        }
    }

    @Override
    public void onShowMoreToggled(boolean isShown) {
        ImageView arrowImage = findViewById(R.id.showMoreIcon);
        NestedScrollView scrollView = findViewById(R.id.mainView);
        LinearLayout actionPanel = findViewById(R.id.showMorePanel);
        int scrollTo = actionPanel.getTop() - 10;

        rotateView(arrowImage, isShown);
        if (isShown)
            scrollView.scrollTo(0, 0);
        else
            scrollView.scrollTo(0, scrollTo);
    }

    private void rotateView(View view, boolean isShown) {
        Animation rotateAni = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateAni.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int degrees = isShown ? 0 : 180;
                ImageView imageView = findViewById(R.id.showMoreIcon);
                Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_show_arrow);
                Matrix matrix = new Matrix();
                matrix.postRotate(degrees);

                Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                        matrix, true);

                imageView.setImageBitmap(rotated);
            }
        });
        view.startAnimation(rotateAni);
    }

    @Override
    public void onStatementTapped(String contractId) {
        Intent intent = new Intent(this, StatementsActivity.class);
        intent.putExtra(StatementsActivity.CONTRACT_ID, contractId);
        startActivity(intent);
    }

    @Override
    public void onTransactionHistoryTapped(HcContract hcContract) {
        showMessage("Transaction History Tapped");
    }

    @Override
    public void onRepaymentHistoryTapped(HcContract hcContract) {
        showMessage("Repayment History Tapped");
    }

    @Override
    public void onHoldTransactionTapped(HcContract hcContract) {
        showMessage("Hold Transaction Tapped");
    }

    @Override
    public void onPaymentLocationTapped(HcContract hcContract) {
        PayMapActivity.start(this, PayMapActivity.PAYMENT_MODE);
    }
}
