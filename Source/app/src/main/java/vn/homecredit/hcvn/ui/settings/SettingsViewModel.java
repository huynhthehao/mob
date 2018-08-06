package vn.homecredit.hcvn.ui.settings;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.CountryValue;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel {
    private final DataManager dataManager;
    private final OneSignalService oneSignalService;
    private ObservableField<String> appVersion = new ObservableField<>("");
    private ObservableField<Integer> languageValue = new ObservableField<>(R.string.english);
    private ObservableField<Boolean> fingerPrintVisibility = new ObservableField<>(true);
    private ObservableField<Boolean> notificationChecked = new ObservableField<>(false);
    private ObservableField<Boolean> fingerPrintChecked = new ObservableField<>(false);


    private MutableLiveData<Boolean> modelBack = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelAppRating = new MutableLiveData<>();
    private MutableLiveData<String> modelLanguage = new MutableLiveData<>();


    @Inject
    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, OneSignalService oneSignalService) {
        super(schedulerProvider);
        this.dataManager = dataManager;
        this.oneSignalService = oneSignalService;
    }

    @Override
    public void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
        languageValue.set(CountryValue.getDisplayNameResIdFromCode(dataManager.getLanguageCode()));
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
        String languageCode = "";
        if (languageValue.get() == CountryValue.VIETNAMESE.getDisplayNameResId()) {
            languageValue.set(CountryValue.ENGLISH.getDisplayNameResId());
            languageCode = CountryValue.ENGLISH.getLanguageCode();
            modelLanguage.setValue(languageCode);
        } else {
            languageValue.set(CountryValue.VIETNAMESE.getDisplayNameResId());
            languageCode = CountryValue.VIETNAMESE.getLanguageCode();
            modelLanguage.setValue(languageCode);
        }
        dataManager.setLanguageCode(languageCode);
    }

    public void onNotificationCheckedChanged(boolean isEnable) {
        dataManager.setNotificationSetting(isEnable);
        oneSignalService.sendTags("Active", String.valueOf(isEnable));
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
