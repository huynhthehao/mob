/*
 * ContractRecyclerViewAdapter.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:11 PM
 */

package vn.homecredit.hcvn.ui.contract.main;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ItemContractActiveBinding;
import vn.homecredit.hcvn.databinding.ItemContractCloseBinding;
import vn.homecredit.hcvn.databinding.ItemContractPendingBinding;
import vn.homecredit.hcvn.utils.DateUtils;
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
        return items.get(position).getTypeStatus();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HcContract.STATUS_ACTIVE) {
            ItemContractActiveBinding itemContractApprovedBinding = ItemContractActiveBinding.inflate(layoutInflater, parent, false);
            return new ActiveViewHolder(itemContractApprovedBinding, onContractListener);
        }else if (viewType == HcContract.STATUS_PENDING) {
            ItemContractPendingBinding itemContractPendingBinding = ItemContractPendingBinding.inflate(layoutInflater,parent, false);
            return new PendingViewHolder(itemContractPendingBinding, onContractListener);
        }else {
            ItemContractCloseBinding itemContractCloseBinding = ItemContractCloseBinding.inflate(layoutInflater, parent, false);
            return new CloseViewHolder(itemContractCloseBinding, onContractListener);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HcContract hcContract = items.get(position);
        if (hcContract.getTypeStatus() == HcContract.STATUS_ACTIVE) {
            ((ActiveViewHolder) holder).bind(hcContract);
        }else if (hcContract.getTypeStatus() == HcContract.STATUS_PENDING) {
            ((PendingViewHolder) holder).bind(hcContract);
        }else if (hcContract.getTypeStatus() == HcContract.STATUS_CLOSED) {
            ((CloseViewHolder) holder).bind(hcContract);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ActiveViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractActiveBinding binding;
        public ActiveViewHolder(ItemContractActiveBinding binding, OnContractListener onContractListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onClicked(getLayoutPosition());
                }
            });
            binding.tvPaymentLocation.setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onLocationClicked(getLayoutPosition());
                }
            });
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            if (!hcContract.isShowSection()) {
                binding.tvSection.setVisibility(View.GONE);
            }else {
                binding.tvSection.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractPendingBinding binding;

        public PendingViewHolder(ItemContractPendingBinding binding, OnContractListener onContractListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onSignClicked(getLayoutPosition());
                }
            });
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            if (!hcContract.isShowSection()) {
                binding.tvSection.setVisibility(View.GONE);
            }else {
                binding.tvSection.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }
    }

    public class CloseViewHolder extends RecyclerView.ViewHolder {

        private final ItemContractCloseBinding binding;

        public CloseViewHolder(ItemContractCloseBinding binding, OnContractListener onContractListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                if (onContractListener != null) {
                    onContractListener.onClicked(getLayoutPosition());
                }
            });
        }

        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            if (!hcContract.isShowSection()) {
                binding.tvSection.setVisibility(View.GONE);
            }else {
                binding.tvSection.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }
    }

    public interface OnContractListener {
        void onClicked(int position);
        void onLocationClicked(int position);
        void onSignClicked(int position);
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
        if (duedate == null) {
            return;
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(total);
        String duedateDisplay = DateUtils.convertDateFromUTCToSimple(duedate);
        tv.setText(Html.fromHtml(tv.getContext().getResources().getString(R.string.nextpayment, formattedNumber, duedateDisplay)));
    }



}
