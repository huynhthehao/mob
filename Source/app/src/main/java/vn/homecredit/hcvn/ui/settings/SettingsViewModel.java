package vn.homecredit.hcvn.ui.settings;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.view.View;

import javax.inject.Inject;

import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.local.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppUtils;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel {
    private FingerPrintHelper fingerPrintHelper;
    private ObservableField<String> appVersion = new ObservableField<>("");
    private ObservableField<Integer> languageValue = new ObservableField<>(R.string.vietnamese);
    private ObservableField<Boolean> fingerPrintVisibility = new ObservableField<>(true);

    private MutableLiveData<Boolean> modelBack = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelAppRating = new MutableLiveData<>();


    @Inject
    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, FingerPrintHelper fingerPrintHelper) {
        super(dataManager, schedulerProvider);
        this.fingerPrintHelper = fingerPrintHelper;
    }

    public void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
        languageValue.set(R.string.vietnamese);
        if (fingerPrintHelper.getFingerPrintAuthValue() == FingerPrintAuthValue.NOT_SUPPORT) {
            fingerPrintVisibility.set(false);
        } else {
            fingerPrintVisibility.set(true);
        }
        modelBack.setValue(false);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public ObservableField<Integer> getLanguageValue() {
        return languageValue;
    }

    public void onBackClicked() {
        modelBack.setValue(true);
    }

    public void onChangeLanguageClicked() {
        if (languageValue.get() == R.string.vietnamese) {
            languageValue.set(R.string.english);
        } else {
            languageValue.set(R.string.vietnamese);
        }
    }

    public void onNotificationCheckedChanged(boolean checked) {

    }

    public void onFingerPrintCheckedChanged(boolean checked) {

    }

    public void onAppRatingClicked() {
        modelAppRating.setValue(true);
    }

    public MutableLiveData<Boolean> getModelBack() {
        return modelBack;
    }

    public MutableLiveData<Boolean> getModelAppRating() {
        return modelAppRating;
    }

    public ObservableField<Boolean> getFingerPrintVisibility() {
        return fingerPrintVisibility;
    }
}
