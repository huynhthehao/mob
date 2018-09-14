package vn.homecredit.hcvn.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.CompoundButton;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.contract.scheduleDetail.ScheduleDetailActivity;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {
    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final FingerPrintHelper fingerPrintHelper;
    private final PreferencesHelper preferencesHelper;
    private String mUserName;
    private HomeExtraMothodsListener listener;

    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider, AccountRepository accountRepository, ContractRepository contractRepository,
                         FingerPrintHelper fingerPrintHelper, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.fingerPrintHelper = fingerPrintHelper;
        this.preferencesHelper = preferencesHelper;

        initView();
    }

    public void setListener(HomeExtraMothodsListener listener){
        this.listener = listener;
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

    public void checkAndNavigating() {
        contractRepository.contracts().subscribe(s -> {
            if (s == null || s.getData() == null) {
                processNavigating(null);
                return;
            }

            List<HcContract> contracts = s.getData().getContracts();
            HcContract defaultContract = getDefaultContract(contracts);

            processNavigating(defaultContract);
        }, throwable -> {
            processNavigating(null);
            handleError(throwable);
        });
    }

    private void processNavigating(HcContract contract){
        if(listener != null)
            listener.openDefaultScheduleDetails(contract);
    }

    private HcContract getDefaultContract(List<HcContract> myContracts) {
        if (myContracts == null || myContracts.size() < 1)
            return null;

        HcContract firstContract = null;
        for (HcContract contract : myContracts) {
            if (contract == null)
                continue;

            if (contract.isCreditCard() || contract.getMasterContract() != null)
                continue;

            firstContract = contract;
            break;
        }

        return firstContract;
    }

    public String getUserName() {
        return mUserName;
    }
}
