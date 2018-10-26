package vn.homecredit.hcvn.ui.home;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.RestUrl;
import vn.homecredit.hcvn.data.remote.UploadAsyncListener;
import vn.homecredit.hcvn.data.remote.UploadAsyncTask;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.helpers.FileHelper;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.CredoService;
import vn.homecredit.hcvn.service.VersionService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {
    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final FingerPrintHelper fingerPrintHelper;
    private final PreferencesHelper preferencesHelper;
    private final CredoService credoService;
    private final VersionService versionService;
    private final RestService restService;
    private String mUserName;
    private HomeExtraMothodsListener listener;

    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider, AccountRepository accountRepository, RestService restService, ContractRepository contractRepository,
                         FingerPrintHelper fingerPrintHelper, PreferencesHelper preferencesHelper, CredoService credoService, VersionService versionService) {
        super(schedulerProvider);
        this.restService = restService;
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.fingerPrintHelper = fingerPrintHelper;
        this.preferencesHelper = preferencesHelper;
        this.credoService = credoService;
        this.versionService = versionService;

        initView();
    }

    public void setListener(HomeExtraMothodsListener listener) {
        this.listener = listener;
    }

    private void initView() {
        if (accountRepository != null
                && accountRepository.getCachedProfile() != null
                && accountRepository.getCachedProfile().getFullName() != null) {
            mUserName = accountRepository.getCachedProfile().getFullName();
        } else {
            mUserName = "";
        }
    }

    public boolean canShowFingerprintGuiding() {
        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        if (fingerSupportStatus != FingerPrintAuthValue.SUPPORT_AND_ENABLED) {
            return false;
        }

        String keyValue = preferencesHelper.getFingerprintEnableStatus();
        preferencesHelper.setFingerprintEnableStatus();
        return StringUtils.isNullOrEmpty(keyValue);
    }

    public boolean canShowCredoConsentDialog() {
        String keyValue = preferencesHelper.getCredoConsentStatus();
        preferencesHelper.setCredoConsentStatus();
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


    public void collectAndSentCredoData(Context context) {
        ByteArrayOutputStream credoData = credoService.collectData(context);
        ProfileResp.ProfileRespData profileData = preferencesHelper.getProfile();
        final String filePath = String.format("%s/%s.txt.gz",context.getFilesDir(), profileData.getUserName());

        if (credoData == null)
            return;

        if (!FileHelper.saveFile(credoData, filePath))
            return;

        String credoConsent = credoService.getCredoConsent(profileData.getUserId(),
                profileData.getUserName(),
                versionService.getVersion());

        UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(context);
        String requestUrl = RestUrl.getRequestUrl(RestUrl.CREDO_UPLOAD);
        Map<String,String> bodyParams = new HashMap<>();
        bodyParams.put("consentData",credoConsent);
        bodyParams.put("tagData","HomeView");

        uploadAsyncTask.init(requestUrl, filePath, bodyParams, restService.getApiAuthHeaders(true));
        uploadAsyncTask.setListener(new UploadAsyncListener() {
            @Override
            public void onPreExecute() {}

            @Override
            public void onPostExecute(String result) {
                FileHelper.deleteFile(filePath);
            }

            @Override
            public void onProgressUpdate(Integer percentage) {}
        });
        uploadAsyncTask.execute();
    }

    private void processNavigating(HcContract contract) {
        if (listener != null)
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
