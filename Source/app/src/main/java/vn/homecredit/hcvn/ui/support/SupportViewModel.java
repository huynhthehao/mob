package vn.homecredit.hcvn.ui.support;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.repository.SupportRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.tracking.TrackingService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SupportViewModel extends BaseViewModel {
    private final PreferencesHelper preferencesHelper;
    private final SupportRepository supportRepository;
    private final TrackingService trackService;
    private ObservableField<String> customerSupportPhoneField = new ObservableField<>("");
    private ObservableField<String> subjectFeedback = new ObservableField<>("");
    private ObservableField<String> messageFeedback = new ObservableField<>("");
    private ObservableField<String> userPhone = new ObservableField<>("");
    private ObservableField<String> userPhoneHint = new ObservableField<>("");
    private MutableLiveData<Boolean> historyClickEvent = new MutableLiveData<>();
    private MutableLiveData<String> callSupportCenterClickEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> feedbackDoneEvent = new MutableLiveData<>();
    String customerSupportPhone = "", contractId = "";

    @Inject
    public SupportViewModel(SchedulerProvider schedulerProvider, PreferencesHelper preferencesHelper, SupportRepository supportRepository, TrackingService trackService) {
        super(schedulerProvider);
        this.preferencesHelper = preferencesHelper;
        this.supportRepository = supportRepository;
        this.trackService = trackService;
    }

    public void initData() {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData != null && !TextUtils.isEmpty(versionRespData.getCustomerSupportPhone())) {
            customerSupportPhone = versionRespData.getCustomerSupportPhone();
            customerSupportPhoneField.set(customerSupportPhone);
        }
        ProfileResp.ProfileRespData profileResp = preferencesHelper.getProfile();
        if (profileResp != null) {
            userPhoneHint.set(profileResp.getPhoneNumber());

            contractId = profileResp.getContractNumber();
        }
    }

    public ObservableField<String> getCustomerSupportPhoneField() {
        return customerSupportPhoneField;
    }

    public void onHistoryClicked() {
        historyClickEvent.setValue(true);
    }

    public void onClearClicked() {
        resetData();
    }

    private void resetData() {
        subjectFeedback.set("");
        messageFeedback.set("");
        userPhone.set("");
    }

    public void onSubmitClicked() {
        if (TextUtils.isEmpty(subjectFeedback.get())) {
            showMessage(R.string.please_enter_subject);
            return;
        }
        if (TextUtils.isEmpty(messageFeedback.get())) {
            showMessage(R.string.please_enter_feedback);
            return;
        }
        if (TextUtils.isEmpty(userPhone.get())) {
            showMessage(R.string.please_enter_phone_number);
            return;
        }

        setIsLoading(true);
        Disposable disposableMarkAsRead = supportRepository.submitFeedback(subjectFeedback.get(), messageFeedback.get(), userPhone.get(), contractId)
                .subscribe(
                        supportResp -> {
                            setIsLoading(false);
                            if (supportResp.getResponseCode() == 0) {
                                resetData();
                                feedbackDoneEvent.setValue(true);
                            } else {
                                showMessage(supportResp.getResponseMessage());
                            }
                        },
                        throwable -> {
                            setIsLoading(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableMarkAsRead);
    }

    public void onCallSupportCenterClicked() {
        callSupportCenterClickEvent.setValue(customerSupportPhone);
        trackService.sendEvent(R.string.ga_event_support_call_category, R.string.ga_event_support_call_action, R.string.ga_event_support_call_label);
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

    public ObservableField<String> getUserPhoneHint() {
        return userPhoneHint;
    }

    public MutableLiveData<Boolean> getFeedbackDoneEvent() {
        return feedbackDoneEvent;
    }
}
