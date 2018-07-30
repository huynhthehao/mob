package vn.homecredit.hcvn.ui.forgetpassword;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ForgetPasswordViewModel extends BaseViewModel {

    private final AccountRepository accountRepository;
    private final DataManager dataManager;
    ObservableField<String> username = new ObservableField<>("");
    ObservableField<String> contracts = new ObservableField<>("");
    private MutableLiveData<OtpPassParam> modelOtpPassParam = new MutableLiveData<>();
    private MutableLiveData<String> modelDialogContractshelp = new MutableLiveData<>();

    @Inject
    public ForgetPasswordViewModel(SchedulerProvider schedulerProvider, AccountRepository accountRepository, DataManager dataManager) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.dataManager = dataManager;
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public ObservableField<String> getContracts() {
        return contracts;
    }

    public MutableLiveData<OtpPassParam> getModelOtpPassParam() {
        return modelOtpPassParam;
    }

    public MutableLiveData<String> getModelDialogContractshelp() {
        return modelDialogContractshelp;
    }

    public void onClickedNext() {
        verified();
    }

    public void onClickedContractsHelp() {
        modelDialogContractshelp.setValue(dataManager.getVersionRespData().getCustomerSupportPhone());
    }

    private void verified() {
        setIsLoading(true);
        Disposable disposableForgetPassword = accountRepository.forgotPasswordVerify(username.get(), contracts.get())
                .subscribe(otpTimer -> {
                            setIsLoading(false);
                            if (otpTimer == null) return;
                            Log.debug(otpTimer.toString());
                            if (otpTimer.isVerified()) {
                                OtpPassParam otpPassParam = new OtpPassParam(otpTimer, username.get(), contracts.get(), OtpFlow.FORGOT_PASSWORD);
                                modelOtpPassParam.setValue(otpPassParam);
                            }else {
                                showMessage(otpTimer.getResponseMessage());
                            }
                        },
                        throwable -> {
                            setIsLoading(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableForgetPassword);

    }


}
