package vn.homecredit.hcvn.ui.home;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {
    private final AccountRepository accountRepository;
    private final FingerPrintHelper fingerPrintHelper;
    private final PreferencesHelper preferencesHelper;
    private String mUserName;

    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider, AccountRepository accountRepository,
                         FingerPrintHelper fingerPrintHelper, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.fingerPrintHelper = fingerPrintHelper;
        this.preferencesHelper = preferencesHelper;

        initView();
    }

    private void initView(){
        if (accountRepository != null
                && accountRepository.getCachedProfile() != null
                && accountRepository.getCachedProfile().getFullName() != null) {
            mUserName = accountRepository.getCachedProfile().getFullName();
        } else {
            mUserName = "";
        }
    }

    public boolean canShowFingerprintGuiding(){
        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        if(fingerSupportStatus != FingerPrintAuthValue.SUPPORT_AND_ENABLED) {
            return false;
        }

        String keyValue = preferencesHelper.getFingerprintEnableStatus();
        preferencesHelper.setFingerprintEnableStatus();
        return StringUtils.isNullOrEmpty(keyValue);
    }


    public String getUserName() {
        return mUserName;
    }
}
