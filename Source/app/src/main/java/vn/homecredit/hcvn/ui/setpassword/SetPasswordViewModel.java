package vn.homecredit.hcvn.ui.setpassword;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppLogger;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SetPasswordViewModel extends BaseViewModel {

    private final DataManager dataManager;
    private final AccountRepository accountRepository;
    private ObservableField<String> filedPass = new ObservableField<>();
    private ObservableField<String> filedConfirmPass = new ObservableField<>();
    private OtpPassParam otpParam;
    private MutableLiveData<Boolean> modelSignIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelForgetPasswordSuccess = new MutableLiveData<>();
    private MutableLiveData<String> modelDialogPasswordHelp = new MutableLiveData<>();
    private MutableLiveData<Integer> modelErrorMessage = new MutableLiveData<>();

    @Inject
    public SetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AccountRepository accountRepository) {
        super(schedulerProvider);
        this.dataManager = dataManager;
        this.accountRepository = accountRepository;
    }

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

    public MutableLiveData<Boolean> getModelForgetPasswordSuccess() {
        return modelForgetPasswordSuccess;
    }

    public void setOtpParam(OtpPassParam otpParam) {
        this.otpParam = otpParam;
    }

    public void onClickedSignUp() {
        if (isDataValid()) {
            switch (otpParam.getOtpFlow()) {
                case SIGN_UP:
                    signUpThenLogin();
                    break;
                case FORGOT_PASSWORD:
                    forgetPasswordSetNew();
                    break;
            }
        }
    }

    public void onClickedPassowrdHelp() {
        modelDialogPasswordHelp.setValue(dataManager.getVersionRespData().getCustomerSupportPhone());
    }

    private boolean isDataValid() {
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

    private void forgetPasswordSetNew() {
        setIsLoading(true);
        Disposable disposable = accountRepository.forgotPasswordSetNew(otpParam.getPhoneNumber(), otpParam.getContractId(), otpParam.getOtp(), filedPass.get())
                .subscribe(profileResp -> {
                    setIsLoading(false);
                    if (profileResp == null) {
                        return;
                    }
                    if (profileResp.getResponseCode() != ProfileResp.RESPONSE_CODE_SUCCESS) {
                        showMessage(profileResp.getResponseMessage());
                    } else {
                        accountRepository.saveLoginInfo(otpParam.getPhoneNumber(), filedPass.get());
                        modelForgetPasswordSuccess.setValue(true);
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                    Crashlytics.logException(AppLogger.createLog(AppLogger.FORGOT_PWD_FAIL, throwable));
                });
        getCompositeDisposable().add(disposable);
    }

    private void signUpThenLogin() {
        setIsLoading(true);
        Disposable disposable = accountRepository.signUpThenLogin(otpParam.getPhoneNumber(), otpParam.getContractId(), otpParam.getOtp(), filedPass.get())
                .subscribe(profileResp -> {
                    setIsLoading(false);
                    if (profileResp == null) {
                        return;
                    }
                    if (profileResp.getResponseCode() != ProfileResp.RESPONSE_CODE_SUCCESS) {
                        showMessage(profileResp.getResponseMessage());
                    } else {
                        accountRepository.saveLoginInfo(otpParam.getPhoneNumber(), filedPass.get());
                        modelSignIn.setValue(true);
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                    Crashlytics.logException(AppLogger.createLog(AppLogger.SIGN_UP_FAIL, throwable));
                });
        getCompositeDisposable().add(disposable);
    }

}
