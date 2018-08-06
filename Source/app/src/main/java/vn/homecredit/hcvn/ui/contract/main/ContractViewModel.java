package vn.homecredit.hcvn.ui.contract.main;

import android.arch.lifecycle.MutableLiveData;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractViewModel extends BaseViewModel {

    private final ContractRepository contractRepository;

    private MutableLiveData<List<HcContract>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    private MutableLiveData<Boolean> errorAuthenticate = new MutableLiveData<>();

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

    @Override
    public void init() {
        super.init();
        errorAuthenticate.setValue(false);
        refreshing.setValue(false);
        getContracṭ̣();
    }

    private void getContracṭ̣() {
        refreshing.setValue(true);
        contractRepository.contracts().subscribe(s -> {
            refreshing.setValue(false);
            listMutableLiveData.setValue(s.getData().getContracts());
        }, throwable -> {
            refreshing.setValue(false);
            handleError(throwable);
            if (throwable instanceof ANError) {
                if (((ANError) throwable).getErrorCode() == HcApiException.ERROR_CODE_UNAUTHORIZED) {
                    errorAuthenticate.setValue(true);
                }
            }
        });
    }

    public void onRefresh() {
        getContracṭ̣();
    }
}
