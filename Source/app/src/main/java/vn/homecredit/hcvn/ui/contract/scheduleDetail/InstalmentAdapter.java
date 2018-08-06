package vn.homecredit.hcvn.ui.contract.scheduleDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.StringUtils;

public class InstalmentAdapter extends RecyclerView.Adapter<InstalmentAdapter.ViewHolder> {
    Context context;
    List<ScheduleDetailResp.Instalment> listData;
    OnItemNotificationClickListener listener;

    public InstalmentAdapter(Context context, OnItemNotificationClickListener listener) {
        listData = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public void swapData(List<ScheduleDetailResp.Instalment> listData) {
        if (listData == null) {
            return;
        }
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_instalment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleDetailResp.Instalment model = listData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vContent;
        TextView dueDateTextView;
        TextView paymentTypeTextView;
        TextView amountDueTextView;
        ImageView passImageView;

        public ViewHolder(View view) {
            super(view);
            vContent = view.findViewById(R.id.vContent);
            dueDateTextView = view.findViewById(R.id.dueDateTextView);
            paymentTypeTextView = view.findViewById(R.id.paymentTypeTextView);
            amountDueTextView = view.findViewById(R.id.amountDueTextView);
            passImageView = view.findViewById(R.id.passImageView);
        }

        public void bind(ScheduleDetailResp.Instalment model) {
            if (!TextUtils.isEmpty(model.getInstalmentDate())) {
                dueDateTextView.setText(DateUtils.convertDateFromUTCToSimple(model.getInstalmentDate()));
            } else {
                dueDateTextView.setVisibility(View.INVISIBLE);
            }
            paymentTypeTextView.setText(model.getRegularityTextVn());
            if (model.getAmount() != null) {
                amountDueTextView.setText(Html.fromHtml(paymentTypeTextView.getContext()
                        .getString(R.string.currency, StringUtils.getCurrencyMaskValue(model.getAmount()))));
            }
            passImageView.setVisibility(model.getInstalmentStatus() == 1 ? View.VISIBLE : View.INVISIBLE);
            vContent.setOnClickListener(view -> listener.onItemNotificationClicked(model));
        }
    }

    public interface OnItemNotificationClickListener {
        void onItemNotificationClicked(ScheduleDetailResp.Instalment model);
    }
}
