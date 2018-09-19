package vn.homecredit.hcvn.ui.payment.momo.whichContract;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class WhichContractViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> modelMyContract = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelPayForOther = new MutableLiveData<>();

    @Inject
    public WhichContractViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public MutableLiveData<Boolean> getModelMyContract() {
        return modelMyContract;
    }

    public MutableLiveData<Boolean> getModelPayForOther() {
        return modelPayForOther;
    }

    public void onMyContractClicked() {
        modelMyContract.setValue(true);
    }

    public void onPayForOtherClicked() {
        modelPayForOther.setValue(true);
    }
}
