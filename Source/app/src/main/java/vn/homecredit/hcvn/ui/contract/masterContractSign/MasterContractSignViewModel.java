package vn.homecredit.hcvn.ui.contract.masterContractSign;

import android.databinding.ObservableField;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyDataResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractSignViewModel extends BaseViewModel {

    private ObservableField<Boolean> isErrorSign = new ObservableField<>();
    private OtpPassParam otpParam;
    private ContractRepository contractRepository;

    @Inject
    public MasterContractSignViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public ObservableField<Boolean> getIsErrorSign() {
        return isErrorSign;
    }

    public void onDoneClicked() {

    }


    public void setOtpParam(OtpPassParam otpParam) {
        this.otpParam = otpParam;
    }

    public OtpPassParam getOtpParam() {
        return otpParam;
    }

    private void checkMasterContractVerified() {
        if (otpParam == null) return;
        MasterContractVerifyDataResp masterContractVerifyDataResp = otpParam.getMasterContractVerifyDataResp();
        if (masterContractVerifyDataResp == null) return;
        contractRepository.checkMasterContractVerified(otpParam.getContractId(), masterContractVerifyDataResp.getTimeOut(), masterContractVerifyDataResp.getLoadInterval())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean != null && aBoolean == Boolean.TRUE) {
                            showSuccessPage();
                        }else {
                            isErrorSign.set(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleError(throwable);
                    }
                });

    }

    private void showSuccessPage() {

    }
}
