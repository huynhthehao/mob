package vn.homecredit.hcvn.ui.settings.changepass;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;
import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.account.AccountRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ChangePassViewModel extends BaseViewModel {

    private final AccountRepository accountRepository;
    private final DataManager dataManager;
    private ObservableField<String> filedPass = new ObservableField<>();
    private ObservableField<String> filedNewPass = new ObservableField<>();
    private ObservableField<String> filedConfirmNewPass = new ObservableField<>();
    private MutableLiveData<String> modelDialogPasswordHelp = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelChangePassSuccess = new MutableLiveData<>();

    @Inject
    public ChangePassViewModel(AccountRepository accountRepository, SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.dataManager = dataManager;
    }

    public ObservableField<String> getFiledPass() {
        return filedPass;
    }

    public ObservableField<String> getFiledNewPass() {
        return filedNewPass;
    }

    public ObservableField<String> getFiledConfirmNewPass() {
        return filedConfirmNewPass;
    }

    public MutableLiveData<String> getModelDialogPasswordHelp() {
        return modelDialogPasswordHelp;
    }

    public MutableLiveData<Boolean> getModelChangePassSuccess() {
        return modelChangePassSuccess;
    }

    public void onClickedNext() {
        if (checkDataValid()) {
            changePass();
        }
    }

    public void onClickedPassowrdHelp() {
        modelDialogPasswordHelp.setValue(dataManager.getVersionRespData().getCustomerSupportPhone());
    }

    private void changePass() {
        setIsLoading(true);
        Disposable disposable = accountRepository.changePassword(filedPass.get(), filedNewPass.get())
                .subscribe(otpTimerResp -> {
                    setIsLoading(false);
                    if (otpTimerResp == null) return;
                    if (otpTimerResp.isVerified()) {
                        accountRepository.updatePassword(filedNewPass.get());
                        modelChangePassSuccess.setValue(true);
                    } else {
                        showMessage(otpTimerResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        getCompositeDisposable().add(disposable);
    }

    private boolean checkDataValid() {
        if (TextUtils.isEmpty(filedPass.get())) {
            showMessage(R.string.error_changepass_current_pass);
            return false;
        }
        if (TextUtils.isEmpty(filedNewPass.get())) {
            showMessage(R.string.error_changepass_new_pass);
            return false;
        }
        if (TextUtils.isEmpty(filedConfirmNewPass.get())) {
            showMessage(R.string.error_changepass_new_confirm_pass);
            return false;
        }
        if (!filedNewPass.get().equals(filedConfirmNewPass.get())) {
            showMessage(R.string.error_password_unmatch);
            return false;
        }
        return true;
    }

}
