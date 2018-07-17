package vn.homecredit.hcvn.ui.settings;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel {
    private ObservableField<String> appVersion = new ObservableField<>("");
    private ObservableField<Integer> languageValue = new ObservableField<>(R.string.vietnamese);
    private MutableLiveData<Boolean> modelBack = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
        languageValue.set(R.string.vietnamese);
        modelBack.setValue(false);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public ObservableField<Integer> getLanguageValue() {
        return languageValue;
    }

    public void onBackClicked(){
        modelBack.setValue(true);
    }

    public void onChangeLanguageClicked() {

    }

    public void onNotificationCheckedChanged(boolean checked) {

    }

    public void onFingerPrintCheckedChanged(boolean checked) {

    }

    public void onAppRatingClicked(){

    }

    public MutableLiveData<Boolean> getModelBack() {
        return modelBack;
    }
}
