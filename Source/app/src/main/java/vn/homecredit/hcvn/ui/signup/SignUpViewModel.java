package vn.homecredit.hcvn.ui.signup;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SignUpViewModel extends BaseViewModel {

    private final AccountRepository accountRepository;
    private final DataManager dataManager;
    ObservableField<String> username = new ObservableField<>("");
    ObservableField<String> contracts = new ObservableField<>("");
    private MutableLiveData<OtpPassParam> modelOtpPassParam = new MutableLiveData<>();
    private MutableLiveData<String> modelDialogContractshelp = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(AccountRepository accountRepository, DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.dataManager = dataManager;
    }

    public MutableLiveData<String> getModelDialogContractshelp() {
        return modelDialogContractshelp;
    }

    public MutableLiveData<OtpPassParam> getModelOtpPassParam() {
        return modelOtpPassParam;
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public ObservableField<String> getContracts() {
        return contracts;
    }

    public void onClickedSignUp() {
        // TODO: This is a work-around stuff. Need to implement at the server side
        if(contracts.get().length() != AppConstants.CONTRACT_NUMBER_LENGTH){
            showMessage(R.string.paymomo_others_contract_wrong_length);
            return;
        }

        verified();
    }

    public void onClickedContractsHelp() {
        modelDialogContractshelp.setValue(dataManager.getVersionRespData().getCustomerSupportPhone());
    }

    private void verified() {
        setIsLoading(true);
        Disposable disposableSignup = accountRepository.signupVerify(username.get(), contracts.get())
                .subscribe(otpTimer -> {
                            setIsLoading(false);
                            if (otpTimer == null) return;
                            if (otpTimer.isVerified()) {
                                OtpPassParam otpPassParam = new OtpPassParam(otpTimer, username.get(), contracts.get(), OtpFlow.SIGN_UP);
                                modelOtpPassParam.setValue(otpPassParam);
                            }else {
                                showMessage(otpTimer.getResponseMessage());
                            }
                        },
                        throwable -> {
                            Crashlytics.logException(new Throwable("SignUp fail!", throwable));
                            setIsLoading(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableSignup);
    }
    // TODO: 17/07/2018 Check dialog language, check  icon edittext

}
