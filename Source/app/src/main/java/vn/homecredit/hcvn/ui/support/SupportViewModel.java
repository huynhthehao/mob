package vn.homecredit.hcvn.ui.support;

import android.databinding.ObservableField;
import android.text.TextUtils;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SupportViewModel extends BaseViewModel {
    private final PreferencesHelper preferencesHelper;
    private ObservableField<String> customerSupportPhoneField = new ObservableField<>("");

    @Inject
    public SupportViewModel(SchedulerProvider schedulerProvider, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData != null && !TextUtils.isEmpty(versionRespData.getCustomerSupportPhone())) {
            customerSupportPhoneField.set(versionRespData.getCustomerSupportPhone());
        }
    }

    public ObservableField<String> getCustomerSupportPhoneField() {
        return customerSupportPhoneField;
    }
}
