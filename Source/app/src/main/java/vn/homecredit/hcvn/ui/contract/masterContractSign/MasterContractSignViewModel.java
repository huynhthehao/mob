package vn.homecredit.hcvn.ui.contract.masterContractSign;

import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractSignViewModel extends BaseViewModel {

    private ObservableField<Boolean> isErrorSign = new ObservableField<>();
    private OtpPassParam otpParam;

    @Inject
    public MasterContractSignViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
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
}
