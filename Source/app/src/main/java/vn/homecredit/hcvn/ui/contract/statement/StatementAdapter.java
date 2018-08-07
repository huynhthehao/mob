package vn.homecredit.hcvn.ui.contract.statement;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.LanguageCode;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.ViewHolder> {
    List<StatementModel> listData;
    OnStatementClickListener listener;
    private PreferencesHelper preferencesHelper;

    public StatementAdapter(OnStatementClickListener onStatementClickListener, PreferencesHelper preferencesHelper) {
        this.listener = onStatementClickListener;
        this.preferencesHelper = preferencesHelper;
        this.listData = new ArrayList<>();
    }

    public void swapData(List<StatementModel> listData) {
        if (listData == null)
            return;
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatementModel model = listData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vContent;
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            vContent = view.findViewById(R.id.vContent);
            tvTitle = view.findViewById(R.id.tvTitle);
        }

        public void bind(StatementModel model) {
            String title = model.getTextVn();
            if (preferencesHelper.getLanguageCode().equalsIgnoreCase(LanguageCode.ENGLISH)) {
                title = model.getTextEn();
            }
            tvTitle.setText(title);
            vContent.setOnClickListener(view -> listener.onStatementClicked(model));
        }
    }

    public interface OnStatementClickListener {
        void onStatementClicked(StatementModel model);
    }

}
