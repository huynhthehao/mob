package vn.homecredit.hcvn.ui.contract.paymentHistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHistoryResp;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.StringUtils;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {
    Context context;
    List<PaymentHistoryResp.Payment> listData;
    PaymentHistoryAdapter.OnItemNotificationClickListener listener;

    public PaymentHistoryAdapter(Context context, PaymentHistoryAdapter.OnItemNotificationClickListener listener) {
        listData = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public void swapData(List<PaymentHistoryResp.Payment> listData) {
        if (listData == null) {
            return;
        }
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_history, parent, false);
        return new PaymentHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.ViewHolder holder, int position) {
        PaymentHistoryResp.Payment model = listData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dueDateTextView;
        TextView paymentTypeTextView;

        public ViewHolder(View view) {
            super(view);
            dueDateTextView = view.findViewById(R.id.textPaidDate);
            paymentTypeTextView = view.findViewById(R.id.textPayAmount);

        }

        public void bind(PaymentHistoryResp.Payment model) {
            if (!TextUtils.isEmpty(model.getPaidDate())) {
                dueDateTextView.setText(DateUtils.convertDateFromUTCToSimple(model.getPaidDate()));
            } else {
                dueDateTextView.setVisibility(View.INVISIBLE);
            }
            if (model.getPaidAmount() != null) {
                paymentTypeTextView.setText(Html.fromHtml(paymentTypeTextView.getContext()
                        .getString(R.string.currency, StringUtils.getCurrencyMaskValue(model.getPaidAmount()))));
            }


        }
    }

    public interface OnItemNotificationClickListener {
        void onItemNotificationClicked(PaymentHistoryResp.Payment model);
    }
}
