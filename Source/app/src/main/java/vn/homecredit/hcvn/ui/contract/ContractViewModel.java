package vn.homecredit.hcvn.ui.contract;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractViewModel extends BaseViewModel {

    private final ContractRepository contractRepository;

    private MutableLiveData<List<HcContract>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> refreshing = new MutableLiveData<>();

    @Inject
    public ContractViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public MutableLiveData<Boolean> getRefreshing() {
        return refreshing;
    }

    public MutableLiveData<List<HcContract>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    @Override
    public void init() {
        super.init();
        getContracṭ̣();
    }

    private void getContracṭ̣() {
        setIsLoading(true);
        refreshing.setValue(true);
        contractRepository.contracts().subscribe(s -> {
            setIsLoading(false);
            refreshing.setValue(false);
            Log.debug(s.getData().getContracts().size() + "");
            listMutableLiveData.setValue(s.getData().getContracts());

        }, throwable -> {
            setIsLoading(false);
            refreshing.setValue(false);
            handleError(throwable);
        });
    }

    public void onRefresh() {
        getContracṭ̣();
    }
}
