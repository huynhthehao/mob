package vn.homecredit.hcvn.ui.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotificationModel> listData;
    OnItemNotificationClickListener listener;

    public NotificationAdapter(OnItemNotificationClickListener listener) {
        listData = new ArrayList<>();
        this.listener = listener;
    }

    public void swapData(List<NotificationModel> listData) {
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
            tvTime.setText(model.getTime());
            tvDescription.setText(model.getMessageText());
            if (model.isRead()) {
                ivHistory.setImageResource(R.drawable.ic_history_read);
            } else {
                ivHistory.setImageResource(R.drawable.ic_hisroty_unread);
            }
            ivIcon.setImageResource(model.getType() != NotificationType.UPDATE.getType() ? NotificationType.getImageResourceIdByType(model.getType())
                    : NotificationType.getResouceIconIdForUpdate(model.isNeedToUpdate()));

            vContent.setOnClickListener(view -> listener.onItemNotificationClicked(model));
        }
    }

    public interface OnItemNotificationClickListener {
        void onItemNotificationClicked(NotificationModel model);
    }
}
