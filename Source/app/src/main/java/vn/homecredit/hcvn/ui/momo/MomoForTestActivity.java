package vn.homecredit.hcvn.ui.momo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.payment.PaymentMomoEventValueBuilder;
import vn.homecredit.hcvn.ui.payment.summary.PaymentSummaryActivity;
import vn.homecredit.hcvn.ui.payment.summary.model.PaymentSummaryModel;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class MomoForTestActivity extends AppCompatActivity {
    TextView tvMessage;

    private String amount = "10000";
    private String fee = "0";
    private String merchantName = "HomeCredit Viet Nam";
    private String merchantCode = "MOMOIQA420180417";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Trả mua xe Audi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momo_test);
        // Development environment, need to switch to Produce when release
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);

        Button btPayForMomo = findViewById(R.id.btPayForMomo);
        btPayForMomo.setOnClickListener(view -> requestPayment());
        tvMessage = findViewById(R.id.tvMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));

                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        // test
                        PaymentSummaryModel model = new PaymentSummaryModel();
                        model.setPaymentDate("20/08/2018");
                        model.setBeneficiary("HomeCredit Viet Nam");
                        model.setPayerName("Nguyen Giang Sy");
                        model.setTotalTransaction("900.000 VND");
                        PaymentSummaryActivity.start(MomoForTestActivity.this, model);
                        // end test
                    } else {
                        tvMessage.setText("message: " + this.getString(R.string.momo_not_receive_info));
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
                    tvMessage.setText("message: " + message);
                } else if (data.getIntExtra("status", -1) == 2) {
                    tvMessage.setText("message: " + this.getString(R.string.momo_not_receive_info));
                } else {
                    tvMessage.setText("message: " + this.getString(R.string.momo_not_receive_info));
                }
            } else {
                tvMessage.setText("message: " + this.getString(R.string.momo_not_receive_info));
            }
        } else {
            tvMessage.setText("message: " + this.getString(R.string.momo_not_receive_info_err));
        }
    }

    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new PaymentMomoEventValueBuilder(this)
                .setAmount(amount)
                .setContractNumber("235234747")
                .create();
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
}
