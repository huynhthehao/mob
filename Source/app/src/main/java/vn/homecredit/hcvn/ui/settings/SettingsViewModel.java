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
import vn.homecredit.hcvn.utils.LanguageValue;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel {
    private final DataManager dataManager;
    private ObservableField<String> appVersion = new ObservableField<>("");
    private ObservableField<Integer> languageValue = new ObservableField<>(R.string.english);
    private ObservableField<Boolean> fingerPrintVisibility = new ObservableField<>(true);
    private ObservableField<Boolean> notificationChecked = new ObservableField<>(false);
    private ObservableField<Boolean> fingerPrintChecked = new ObservableField<>(false);


    private MutableLiveData<Boolean> modelBack = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelAppRating = new MutableLiveData<>();
    private MutableLiveData<String> modelLanguage = new MutableLiveData<>();


    @Inject
    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.dataManager = dataManager;
    }

    @Override
    public void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
        languageValue.set(LanguageValue.VIETNAMESE.getDisplayNameResId());
        checkToShowOrHideFingerPrintLayout();
        modelBack.setValue(false);
        fingerPrintChecked.set(dataManager.getFingerPrintSetting());
        notificationChecked.set(dataManager.getNotificationSetting());
    }

    private void checkToShowOrHideFingerPrintLayout() {
        if (dataManager.getFingerPrintAuthValue() == FingerPrintAuthValue.NOT_SUPPORT) {
            fingerPrintVisibility.set(false);
        } else {
            fingerPrintVisibility.set(true);
        }
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
        if (languageValue.get() == LanguageValue.VIETNAMESE.getDisplayNameResId()) {
            languageValue.set(LanguageValue.ENGLISH.getDisplayNameResId());
            modelLanguage.setValue(LanguageValue.ENGLISH.getCode());
        } else {
            languageValue.set(LanguageValue.VIETNAMESE.getDisplayNameResId());
            modelLanguage.setValue(LanguageValue.VIETNAMESE.getCode());
        }
    }

    public void onNotificationCheckedChanged(boolean isEnable) {
        dataManager.setNotificationSetting(isEnable);
    }

    public void onFingerPrintCheckedChanged(boolean isEnable) {
        dataManager.setFingerPrintSetting(isEnable);
        // TODO in case turn on this setting, check if user do not enable finger print feature, show message to user.
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

    public MutableLiveData<String> getModelLanguage() {
        return modelLanguage;
    }

    public ObservableField<Boolean> getFingerPrintVisibility() {
        return fingerPrintVisibility;
    }

    public ObservableField<Boolean> getNotificationChecked() {
        return notificationChecked;
    }

    public ObservableField<Boolean> getFingerPrintChecked() {
        return fingerPrintChecked;
    }
}
