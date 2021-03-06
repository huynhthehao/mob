package vn.homecredit.hcvn.ui.contract.scheduleDetail;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ScheduleDetailViewModel extends BaseViewModel {

    private String contractNumber;

    private final ContractRepository contractRepository;
    private MutableLiveData<List<ScheduleDetailResp.Instalment>> dataCollection = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();

    @Inject
    public ScheduleDetailViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void initData(String contractNumber) {
        super.init();
        setContractNumber(contractNumber);
        modelIsRefreshing.setValue(true);
        refreshCollection();
    }

    private void refreshCollection() {
        Disposable refreshTrigger = contractRepository.viewInstalmentsv1(contractNumber)
                .subscribe(
                        response ->
                        {
                            modelIsRefreshing.setValue(false);
                            display(response);
                        },
                        throwable -> {
                            modelIsRefreshing.setValue(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(refreshTrigger);
    }

    private void display(ScheduleDetailResp scheduleDetailResp) {
        if (scheduleDetailResp != null && scheduleDetailResp.getData() != null) {
            dataCollection.setValue(scheduleDetailResp.getData());
        }
    }


    public void pullToRefreshCollection() {
        refreshCollection();
    }

    public MutableLiveData<List<ScheduleDetailResp.Instalment>> getDataCollection() {
        return dataCollection;
    }

    public MutableLiveData<Boolean> getModelIsRefreshing() {
        return modelIsRefreshing;
    }


    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
