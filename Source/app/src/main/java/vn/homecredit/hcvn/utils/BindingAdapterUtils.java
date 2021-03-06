package vn.homecredit.hcvn.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.ui.custom.MenuRowItem;
import vn.homecredit.hcvn.ui.custom.PayMomoInfoItem;

public class BindingAdapterUtils {

    @BindingAdapter({"enable"})
    public static void setEnable(MenuRowItem menuRowItem, Boolean enable ) {
        if (enable == Boolean.FALSE) {
            menuRowItem.disable();
        }
    }
    @BindingAdapter({"amount"})
    public static void setLoanAmountPayMomo(PayMomoInfoItem payMomoInfoItem, Integer loanAmount) {
        if (loanAmount == null) return;
        String formattedNumber = StringUtils.getCurrencyMaskValue(loanAmount);
        String displayAmount = payMomoInfoItem.getContext().getString(R.string.currency, formattedNumber);
        payMomoInfoItem.setContent(Html.fromHtml(displayAmount));
    }

    @BindingAdapter({"date"})
    public static void setSingedDate(PayMomoInfoItem payMomoInfoItem, String signedDate) {
        if (signedDate == null) return;
        payMomoInfoItem.setContent(DateUtils.convertDateFromUTCToSimple(signedDate));
    }
    @BindingAdapter({"date"})
    public static void setSingedDate(TextView textView, String signedDate) {
        if (signedDate == null) return;
        textView.setText(DateUtils.convertDateFromUTCToSimple(signedDate));
    }

    @BindingAdapter({"loanamount"})
    public static void setLoanAmount(TextView textView, Integer loanAmount) {
        if (loanAmount == null) return;
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(loanAmount);
        textView.setText(Html.fromHtml(textView.getContext().getString(R.string.currency, formattedNumber)));
    }

    @InverseBindingAdapter(attribute = "loanamount")
    public static int getLoanAmount(TextView textView) {
        NumberFormat formatter = new DecimalFormat("#,##0.##");
        try {
            return formatter.parse(textView.getText().toString()).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @BindingAdapter({"type"})
    public static void setIcon(ImageView iv, int typeContract) {
        switch (typeContract) {
            case HcContract.TYPE_CASH_LOAN:
                iv.setImageResource(R.drawable.ic_contract_cashloan);
                break;
            case HcContract.TYPE_DURABLE:
                iv.setImageResource(R.drawable.ic_consumer_durable);
                break;
            case HcContract.TYPE_CREDIT_CARD:
                iv.setImageResource(R.drawable.ic_contract_creditcard);
                break;
            case HcContract.TYPE_TWO_WHEEL:
                iv.setImageResource(R.drawable.ic_contract_twowheel);
                break;
            default:
                iv.setImageResource(R.drawable.ic_contract_twowheel);
                break;

        }
    }

    @BindingAdapter({"total", "duedate"})
    public static void setNextPayment(TextView tv, int total, String duedate) {
        if (TextUtils.isEmpty(duedate)) {
            return;
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(total);
        String duedateDisplay = DateUtils.convertDateFromUTCToSimple(duedate);
        tv.setText(Html.fromHtml(tv.getContext().getResources().getString(R.string.nextpayment, formattedNumber, duedateDisplay)));
    }



}
