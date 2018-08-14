package vn.homecredit.hcvn.ui.support;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SupportViewModel extends BaseViewModel {
    private final PreferencesHelper preferencesHelper;
    private ObservableField<String> customerSupportPhoneField = new ObservableField<>("");
    private ObservableField<String> subjectFeedback = new ObservableField<>("");
    private ObservableField<String> messageFeedback = new ObservableField<>("");
    private ObservableField<String> userPhone = new ObservableField<>("");
    private MutableLiveData<Boolean> historyClickEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> clearClickEvent = new MutableLiveData<>();
    private MutableLiveData<String> callSupportCenterClickEvent = new MutableLiveData<>();
    String customerSupportPhone = "";

    @Inject
    public SupportViewModel(SchedulerProvider schedulerProvider, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData != null && !TextUtils.isEmpty(versionRespData.getCustomerSupportPhone())) {
            customerSupportPhone = versionRespData.getCustomerSupportPhone();
            customerSupportPhoneField.set(customerSupportPhone);
        }
        ProfileResp.ProfileRespData profileResp = preferencesHelper.getProfile();
        if (profileResp != null) {
            userPhone.set(profileResp.getPhoneNumber());
        }
    }

    public ObservableField<String> getCustomerSupportPhoneField() {
        return customerSupportPhoneField;
    }

    public void onHistoryClicked() {
        historyClickEvent.setValue(true);
    }

    public void onClearClicked() {
        clearClickEvent.setValue(true);
    }

    public void onSubmitClicked() {

    }

    public void onCallSupportCenterClicked() {
        callSupportCenterClickEvent.setValue(customerSupportPhone);
    }

    public ObservableField<String> getSubjectFeedback() {
        return subjectFeedback;
    }

    public ObservableField<String> getMessageFeedback() {
        return messageFeedback;
    }

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public MutableLiveData<String> getCallSupportCenterClickEvent() {
        return callSupportCenterClickEvent;
    }

    public MutableLiveData<Boolean> getHistoryClickEvent() {
        return historyClickEvent;
    }

    public MutableLiveData<Boolean> getClearClickEvent() {
        return clearClickEvent;
    }
}
