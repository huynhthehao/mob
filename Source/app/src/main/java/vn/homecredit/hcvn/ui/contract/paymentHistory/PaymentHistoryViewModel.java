package vn.homecredit.hcvn.ui.contract.paymentHistory;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHisResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


public class PaymentHistoryViewModel extends BaseViewModel {

    private String contractNumber;

    private final ContractRepository contractRepository;
    private MutableLiveData<List<PaymentHisResp.Payment>> dataCollection = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();

    @Inject
    public PaymentHistoryViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    @Override
    public void init() {
        super.init();
    }

    public void init(String contractNumber) {
        super.init();
        setContractNumber(contractNumber);
        modelIsRefreshing.setValue(true);
        refreshCollection();
    }

    private void refreshCollection() {
        Disposable disposableNotifications = contractRepository.viewPaymentsv1(contractNumber)
                .subscribe(
                        paymentResp ->
                        {
                            modelIsRefreshing.setValue(false);
                            loadAndDisplayCachedNotifications(paymentResp);
                        },
                        throwable -> {
                            modelIsRefreshing.setValue(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableNotifications);
    }

    private void loadAndDisplayCachedNotifications(PaymentHisResp scheduleDetailResp) {
        if (scheduleDetailResp != null && scheduleDetailResp.getData() != null) {
            dataCollection.setValue(scheduleDetailResp.getData());
        }
    }


    public void pullToRefreshCollection() {
        refreshCollection();
    }

    public MutableLiveData<List<PaymentHisResp.Payment>> getDataCollection() {
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

