package vn.homecredit.hcvn.ui.notification;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.notification.model.MessageSpan;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;
import vn.homecredit.hcvn.utils.TimeHelper;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<NotificationModel> listData;
    OnItemNotificationClickListener listener;

    public NotificationAdapter(Context context, OnItemNotificationClickListener listener) {
        listData = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public void swapData(List<NotificationModel> listData) {
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
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel model = listData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vContent;
        ImageView ivIcon, ivHistory;
        TextView tvTitle, tvTime, tvDescription;

        public ViewHolder(View view) {
            super(view);
            vContent = view.findViewById(R.id.vContent);
            ivIcon = view.findViewById(R.id.ivIcon);
            ivHistory = view.findViewById(R.id.ivHistory);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvTime = view.findViewById(R.id.tvTime);
            tvDescription = view.findViewById(R.id.tvDescription);
        }

        public void bind(NotificationModel model) {
            tvTitle.setText(model.getTitle());
            tvTime.setText(TimeHelper.getTimeAgo(model.getTime()));
            tvDescription.setText(Html.fromHtml(getDescription(model)));
            if (model.isRead()) {
                ivHistory.setImageResource(R.drawable.ic_history_read);
                tvTime.setTextColor(context.getResources().getColor(R.color.brownishGrey));
            } else {
                ivHistory.setImageResource(R.drawable.ic_hisroty_unread);
                tvTime.setTextColor(context.getResources().getColor(R.color.primary_red));
            }
            ivIcon.setImageResource(model.getType() != NotificationType.UPDATE.getType() ? NotificationType.getImageResourceIdByType(model.getType())
                    : NotificationType.getResouceIconIdForUpdate(model.isNeedToUpdate()));

            vContent.setOnClickListener(view -> listener.onItemNotificationClicked(model));
        }

        private String getDescription(NotificationModel model) {
            String description = model.getMessageText();
            if (model.getOffer() != null || model.getType() == NotificationType.MARKETING.getType()) {
                description = description + " <b><font color='#E53935'>" + context.getString(R.string.see_details) + "</font></b>";
            }
            return description;
        }
    }

    public interface OnItemNotificationClickListener {
        void onItemNotificationClicked(NotificationModel model);
    }
}
