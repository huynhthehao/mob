package vn.homecredit.hcvn.ui.momo.myContract;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentContractListBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.contract.main.ContractRecyclerViewAdapter;
import vn.homecredit.hcvn.ui.contract.main.ContractViewModel;

public class MyContractFragment extends BaseFragment<FragmentContractListBinding, ContractViewModel> implements ContractRecyclerViewAdapter.OnContractListener {
    ContractRecyclerViewAdapter contractRecyclerViewAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contract_list;
    }

    @Override
    public ContractViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ContractViewModel.class);
    }

    @Override
    protected void init() {
        super.init();
        contractRecyclerViewAdapter = new ContractRecyclerViewAdapter();
        contractRecyclerViewAdapter.setOnContractListener(this);
        RecyclerView rvContract = getViewDataBinding().getRoot().findViewById(R.id.rvContract);
        rvContract.setAdapter(contractRecyclerViewAdapter);
        getViewModel().getListMutableLiveData().observe(this, hcContracts -> {
            if (hcContracts != null) {
                contractRecyclerViewAdapter.setNewData(hcContracts);
            }
        });
        getViewModel().getRefreshing().observe(this, aBoolean -> getViewDataBinding().swiperefresh.setRefreshing(aBoolean));
        getViewModel().getActiviteContracṭ̣();

    }

    @Override
    public void onClicked(int position) {

    }

    @Override
    public void onLocationClicked(int position) {
        // Do nothing
    }

    @Override
    public void onSignClicked(int position) {
        // Do nothing

    }
}
