package vn.homecredit.hcvn.ui.contract.masterContractSuccess;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractSuccessViewModel extends BaseViewModel {

    private ObservableField<String> filedContractId = new ObservableField<>();
    private ObservableField<String> fieldSupportPhone = new ObservableField<>();
    private ObservableField<Boolean> fieldIsCreditMaster = new ObservableField<>();
    private MutableLiveData<Boolean> modelDisbursementLocation = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelDone = new MutableLiveData<>();

    private OtpPassParam otpParam;
    private PreferencesHelper preferencesHelper;

    @Inject
    public MasterContractSuccessViewModel(SchedulerProvider schedulerProvider, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        super.init();
    }

    public void setOtpParam(OtpPassParam otpParam) {
        this.otpParam = otpParam;
        filedContractId.set(otpParam.getContractId());
        fieldIsCreditMaster.set(otpParam.getMasterContract().isCreditCardContract());
        fieldSupportPhone.set(preferencesHelper.getVersionRespData().getCustomerSupportPhone());
    }

    public MutableLiveData<Boolean> getModelDisbursementLocation() {
        return modelDisbursementLocation;
    }

    public MutableLiveData<Boolean> getModelDone() {
        return modelDone;
    }

    public OtpPassParam getOtpParam() {
        return otpParam;
    }

    public ObservableField<String> getFiledContractId() {
        return filedContractId;
    }

    public void setFiledContractId(ObservableField<String> filedContractId) {
        this.filedContractId = filedContractId;
    }

    public ObservableField<String> getFieldSupportPhone() {
        return fieldSupportPhone;
    }

    public void setFieldSupportPhone(ObservableField<String> fieldSupportPhone) {
        this.fieldSupportPhone = fieldSupportPhone;
    }

    public ObservableField<Boolean> getFieldIsCreditMaster() {
        return fieldIsCreditMaster;
    }

    public void setFieldIsCreditMaster(ObservableField<Boolean> fieldIsCreditMaster) {
        this.fieldIsCreditMaster = fieldIsCreditMaster;
    }

    public void onDisbursementClicked() {
        modelDisbursementLocation.setValue(true);
    }

    public void onDoneClicked() {
        modelDone.setValue(true);
    }

    @BindingAdapter({"mcId", "isCreditMc", "phone"})
    public static void setMasterContractSuccess(TextView textView, String contractId, boolean isCreditMc, String phone) {
        if (contractId == null) return;
        if (isCreditMc) {
            textView.setText(Html.fromHtml(textView.getContext().getString(R.string.master_contract_credit_success, contractId,phone)));
        } else {
            textView.setText(Html.fromHtml(textView.getContext().getString(R.string.master_contract_success, contractId )));
        }
        Linkify.addLinks(textView, Linkify.PHONE_NUMBERS);
        textView.setLinkTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
    }
}
