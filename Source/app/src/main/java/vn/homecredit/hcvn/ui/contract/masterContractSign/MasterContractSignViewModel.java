package vn.homecredit.hcvn.ui.contract.masterContractSign;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyDataResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractSignViewModel extends BaseViewModel {

    private ObservableField<Boolean> isErrorSign = new ObservableField<>();
    private OtpPassParam otpParam;
    private ContractRepository contractRepository;
    private MutableLiveData<Boolean> modelSuccess = new MutableLiveData<>();
    private Disposable disposableVerifyMasterContract;

    @Inject
    public MasterContractSignViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public ObservableField<Boolean> getIsErrorSign() {
        return isErrorSign;
    }

    public MutableLiveData<Boolean> getModelSuccess() {
        return modelSuccess;
    }

    public void onDoneClicked() {

    }


    public void setOtpParam(OtpPassParam otpParam) {
        this.otpParam = otpParam;
        checkMasterContractVerified();
    }

    public OtpPassParam getOtpParam() {
        return otpParam;
    }

    private void checkMasterContractVerified() {
        if (otpParam == null) return;
        MasterContractVerifyDataResp masterContractVerifyDataResp = otpParam.getMasterContractVerifyDataResp();
        if (masterContractVerifyDataResp == null) return;
        disposableVerifyMasterContract = contractRepository.checkMasterContractVerified(otpParam.getContractId(), masterContractVerifyDataResp.getTimeOut(), masterContractVerifyDataResp.getLoadInterval())
                .subscribe(aBoolean -> {
                    if (aBoolean != null && aBoolean == Boolean.TRUE) {
                        modelSuccess.setValue(true);
                    } else {
                        isErrorSign.set(true);
                    }
                    if (disposableVerifyMasterContract != null){
                        disposableVerifyMasterContract.dispose();
                    }
                }, throwable -> {
                    handleError(throwable);
                });
        getCompositeDisposable().add(disposableVerifyMasterContract);

    }

}
