package vn.homecredit.hcvn.ui.contract.main;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractViewModel extends BaseViewModel {

    private final ContractRepository contractRepository;

    private MutableLiveData<List<HcContract>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    private MutableLiveData<Boolean> errorAuthenticate = new MutableLiveData<>();
    private boolean isLastGetContract = true;

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

    public MutableLiveData<Boolean> getModelReLogin() {
        return errorAuthenticate;
    }

    public HcContract getDataAtIndex(int index){
        try {
            List<HcContract> contracts = listMutableLiveData.getValue();
            return contracts.get(index);
        } catch (Exception ex){
            return null;
        }
    }

    @Override
    public void init() {
        super.init();
        errorAuthenticate.setValue(false);
        refreshing.setValue(false);
    }

    public void getContracṭ̣() {
        isLastGetContract = true;
        refreshing.setValue(true);
        contractRepository.contracts().subscribe(s -> {
            refreshing.setValue(false);
            if (s.getData() != null) {
                listMutableLiveData.setValue(s.getData().getContracts());
            }else {
                showMessage(s.getResponseMessage());
            }
        }, throwable -> {
            refreshing.setValue(false);
            handleError(throwable);
        });
    }

    public void getActiviteContracṭ̣() {
        isLastGetContract = false;
        refreshing.setValue(true);
        contractRepository.activeContracts().subscribe(s -> {
            refreshing.setValue(false);
            if (s.getData() != null) {
                listMutableLiveData.setValue(s.getData().getContracts());
            }else {
                showMessage(s.getResponseMessage());
            }
        }, throwable -> {
            refreshing.setValue(false);
            handleError(throwable);
        });
    }

    public void onRefresh() {
        if (isLastGetContract) {
            getContracṭ̣();
        }else {
            getActiviteContracṭ̣();
        }
    }
}
