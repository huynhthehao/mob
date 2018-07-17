package vn.homecredit.hcvn.ui.setpassword;

import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SetPasswordViewModel extends BaseViewModel {

    private ObservableField<String> filedPass= new ObservableField<>();
    private ObservableField<String> filedConfirmPass = new ObservableField<>();

    @Inject
    public SetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getFiledPass() {
        return filedPass;
    }

    public ObservableField<String> getFiledConfirmPass() {
        return filedConfirmPass;
    }

    public void onClickedSignUp() {

    }
}
