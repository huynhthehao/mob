/*
 * ContractRecyclerViewAdapter.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:11 PM
 */

package vn.homecredit.hcvn.ui.contract;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ItemContractApprovedBinding;
import vn.homecredit.hcvn.databinding.ItemContractCloseBinding;
import vn.homecredit.hcvn.databinding.ItemContractPendingBinding;
import vn.homecredit.hcvn.ui.contract.dummy.DummyContent.DummyItem;
import vn.homecredit.hcvn.utils.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HcContract> items = new ArrayList<>();
    private OnContractListener onContractListener;

    public ContractRecyclerViewAdapter() {
    }

    public void setNewData(List<HcContract> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public HcContract getItem(int position) {
        return items.get(position);
    }

    public void setOnContractListener(OnContractListener onContractListener) {
        this.onContractListener = onContractListener;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeContract();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HcContract.TYPE_CLOSED) {
            ItemContractCloseBinding itemContractCloseBinding = ItemContractCloseBinding.inflate(layoutInflater, parent, false);
            return new CloseViewHolder(itemContractCloseBinding, onContractListener);
        }else if (viewType == HcContract.TYPE_APPROVIED) {
            ItemContractApprovedBinding itemContractApprovedBinding = ItemContractApprovedBinding.inflate(layoutInflater, parent, false);
            return new ApprovedViewHolder(itemContractApprovedBinding);
        }else {
            ItemContractPendingBinding itemContractPendingBinding = ItemContractPendingBinding.inflate(layoutInflater,parent, false);
            return new PendingViewHolder(itemContractPendingBinding, onContractListener);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HcContract hcContract = items.get(position);
        if (hcContract.getTypeContract() == HcContract.TYPE_CLOSED) {
            ((CloseViewHolder) holder).bind(hcContract);
        }else if (hcContract.getTypeContract() == HcContract.TYPE_CLOSED) {
            ((PendingViewHolder) holder).bind(hcContract);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ApprovedViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractApprovedBinding binding;

        public ApprovedViewHolder(ItemContractApprovedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            binding.executePendingBindings();
        }
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractPendingBinding binding;
        private OnContractListener onContractListener;

        public PendingViewHolder(ItemContractPendingBinding binding, OnContractListener onContractListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onContractListener = onContractListener;
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            binding.getRoot().setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onClicked(getLayoutPosition());
                }
            });
            binding.tvSigned.setOnClickListener(view -> Log.debug("click sign"));
            binding.executePendingBindings();
        }
    }

    public class CloseViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractCloseBinding binding;
        private OnContractListener onContractListener;

        public CloseViewHolder(ItemContractCloseBinding binding, OnContractListener onContractListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onContractListener = onContractListener;
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            if (getLayoutPosition() != 0) {
                binding.tvSection.setVisibility(View.GONE);
            }else {
                binding.tvSection.setVisibility(View.VISIBLE);
            }
            binding.getRoot().setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onClicked(getLayoutPosition());
                }
            });
            binding.executePendingBindings();
        }
    }


    public interface OnContractListener {
        void onClicked(int position);
    }

    @BindingAdapter({"date"})
    public static void setSingedDate(TextView textView, String signedDate) {
        if (signedDate == null) return;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date serverDate = simpleDateFormat.parse(signedDate);
            String displayDate = displayFormat.format(serverDate);
            textView.setText(displayDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @BindingAdapter({"loanamount"})
    public static void setLoanAmount(TextView textView, Integer loanAmount) {
        if (loanAmount == null) return;
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(loanAmount);
        textView.setText(formattedNumber + "đ");
    }



}
