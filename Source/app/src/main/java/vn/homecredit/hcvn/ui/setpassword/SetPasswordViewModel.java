package vn.homecredit.hcvn.ui.setpassword;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.account.AccountRepository;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SetPasswordViewModel extends BaseViewModel {

    private final AccountRepository accountRepository;
    private ObservableField<String> filedPass= new ObservableField<>();
    private ObservableField<String> filedConfirmPass = new ObservableField<>();
    private OtpPassParam otpParam;
    private MutableLiveData<Boolean> modelSignIn = new MutableLiveData<>();
    private MutableLiveData<String> modelDialogPasswordHelp = new MutableLiveData<>();
    private MutableLiveData<Integer> modelErrorMessage = new MutableLiveData<>();

    @Inject
    public SetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AccountRepository accountRepository) {
        super(dataManager, schedulerProvider);
        this.accountRepository = accountRepository;
    }

    @Override
    public MutableLiveData<Integer> getModelErrorMessage() {
        return modelErrorMessage;
    }

    public MutableLiveData<String> getModelDialogPasswordHelp() {
        return modelDialogPasswordHelp;
    }

    public MutableLiveData<Boolean> getModelSignIn() {
        return modelSignIn;
    }

    public ObservableField<String> getFiledPass() {
        return filedPass;
    }

    public ObservableField<String> getFiledConfirmPass() {
        return filedConfirmPass;
    }

    public void setOtpParam(OtpPassParam otpParam) {
        this.otpParam = otpParam;
    }

    public void onClickedSignUp() {
        if (checkDataValid()) {
            signUp();
        }
    }
    public void onClickedPassowrdHelp() {
        modelDialogPasswordHelp.setValue(getDataManager().getVersionRespData().getCustomerSupportPhone());
    }

    private boolean checkDataValid() {
        if (TextUtils.isEmpty(filedPass.get())) {
            modelErrorMessage.setValue(R.string.error_password);
            return false;
        }
        if (TextUtils.isEmpty(filedConfirmPass.get())) {
            modelErrorMessage.setValue(R.string.error_password_confirm);
            return false;
        }
        if (!filedPass.get().equals(filedConfirmPass.get())) {
            modelErrorMessage.setValue(R.string.error_password_unmatch);
            return false;
        }
        return true;
    }

    private void signUp() {
        setIsLoading(true);
        Disposable disposable = accountRepository.signUp(otpParam.getPhoneNumber(), otpParam.getContractId(), otpParam.getOtp(), filedPass.get())
                .subscribe(profileResp -> {
                    setIsLoading(false);
                    if (profileResp == null) {
                        return;
                    }
                    if (profileResp.getResponseCode() != 0 ) {
                        setModelErrorMessage(profileResp.getResponseMessage());
                    }else {
                        login();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    Log.printStackTrace(throwable);
                    if (throwable instanceof HcApiException) {
                        setModelErrorMessage(((HcApiException) throwable).getErrorResponseMessage());
                    }

                });
        getCompositeDisposable().add(disposable);
    }

    private void login() {
        Disposable subscribe = accountRepository.signIn(otpParam.getPhoneNumber(), filedPass.get())
                .subscribe(profileResp -> {
                    if (profileResp == null) return;
                    if (profileResp.getResponseCode() != 0) {
                        setModelErrorMessage(profileResp.getResponseMessage());
                    } else {
                        modelSignIn.setValue(true);
                    }
                }, throwable -> Log.printStackTrace(throwable));

        getCompositeDisposable().add(subscribe);
    }

}
