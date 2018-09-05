package vn.homecredit.hcvn.ui.momo.paymentMomo;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.databinding.ObservableField;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.MomoSettingInfo;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentRequestValue;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentRespData;
import vn.homecredit.hcvn.ui.payment.model.PaymentMomoRequestModel;
import vn.homecredit.hcvn.ui.payment.summary.model.PaymentSummaryModel;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;
import vn.momo.momo_partner.AppMoMoLib;

public class PaymentMomoViewModel extends BaseViewModel {

    private ObservableField<RePaymentData> rePaymentData = new ObservableField<>();
    private ContractRepository contractRepository;
    private final PreferencesHelper preferencesHelper;
    private HcContract contract;
    private ObservableField<Boolean> fullPayment = new ObservableField<>(true);
    private ObservableField<Boolean> partialPayment = new ObservableField<>(false);
    private MutableLiveData<PaymentMomoRequestModel> modelRequestPaymentViaMomo = new MutableLiveData<>();
    private MutableLiveData<PaymentSummaryModel> paymentMomoSuccess = new MutableLiveData<>();
    MomoSettingInfo momoSettingInfo;
    private MutableLiveData<Boolean> modelPaymentViaMomo = new MutableLiveData<>();
    private ObservableField<Boolean> bindVisibleContract = new ObservableField<>(false);
    private ObservableField<Boolean> bindVisibleFullname = new ObservableField<>(false);
    private ObservableField<Boolean> bindVisibleCustomerId = new ObservableField<>(false);
    private ObservableField<Boolean> bindVisibleDuedate = new ObservableField<>(false);
    private ObservableField<Boolean> bindVisibleRepayment = new ObservableField<>(false);

    @Inject
    public PaymentMomoViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        super.init();
    }

    public MutableLiveData<PaymentMomoRequestModel> getModelRequestPaymentViaMomo() {
        return modelRequestPaymentViaMomo;
    }
    public ObservableField<Boolean> getBindVisibleRepayment() {
        return bindVisibleRepayment;
    }

    public ObservableField<Boolean> getBindVisibleContract() {
        return bindVisibleContract;
    }

    public ObservableField<Boolean> getBindVisibleFullname() {
        return bindVisibleFullname;
    }

    public ObservableField<Boolean> getBindVisibleCustomerId() {
        return bindVisibleCustomerId;
    }

    public ObservableField<Boolean> getBindVisibleDuedate() {
        return bindVisibleDuedate;
    }

    public MutableLiveData<Boolean> getModelPaymentViaMomo() {
        return modelPaymentViaMomo;
    }

    public ObservableField<RePaymentData> getRePaymentData() {
        return rePaymentData;
    }

    public void setFullPayment(ObservableField<Boolean> fullPayment) {
        this.fullPayment = fullPayment;
    }

    public void setPartialPayment(ObservableField<Boolean> partialPayment) {
        this.partialPayment = partialPayment;
    }

    public ObservableField<Boolean> getFullPayment() {
        return fullPayment;
    }

    public ObservableField<Boolean> getPartialPayment() {
        return partialPayment;
    }

    public void payViaMomoClicked() {
        if (rePaymentData == null)
            return;
        if (partialPayment.get()) {
            //TODO set amount for rePaymentData after verify
        }
        PaymentMomoRequestModel paymentMomoRequestModel = new PaymentMomoRequestModel();
        paymentMomoRequestModel.setRePaymentData(rePaymentData.get());
        if (preferencesHelper.getVersionRespData() != null && preferencesHelper.getVersionRespData().getMomoSettingInfo() != null) {
            momoSettingInfo = preferencesHelper.getVersionRespData().getMomoSettingInfo();
            paymentMomoRequestModel.setMerchantCode(momoSettingInfo.getMerchantCode());
            paymentMomoRequestModel.setMerchantName(momoSettingInfo.getMerchantName());
        }
        paymentMomoRequestModel.setLanguageCode(preferencesHelper.getLanguageCode());
        modelRequestPaymentViaMomo.setValue(paymentMomoRequestModel);
    }

    public void getRepayment() {
        if (contract == null || contract.getContractNumber() == null) {
            return;
        }
        setIsLoading(true);
        bindVisibleRepayment.set(false);
        contractRepository.getRePayment(contract.getContractNumber())
                .subscribe(rePaymentResp -> {
                    setIsLoading(false);
                    if (rePaymentResp == null) {
                        return;
                    }
                    if (rePaymentResp.isSuccess()) {
                        updateData(rePaymentResp.getRePaymentData());
                    } else {
                        showMessage(rePaymentResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }

    private void updateData(RePaymentData rePaymentData) {
        if (rePaymentData == null) {
            return;
        }
        bindVisibleRepayment.set(true);
        this.rePaymentData.set(rePaymentData);
        bindVisibleContract.set(!StringUtils.isNullOrEmpty(rePaymentData.getContractNumber()));
        bindVisibleFullname.set(!StringUtils.isNullOrEmpty(rePaymentData.getFullName()));
        bindVisibleCustomerId.set(!StringUtils.isNullOrEmpty(rePaymentData.getIdNumber()));
        bindVisibleDuedate.set(!StringUtils.isNullOrEmpty(rePaymentData.getDueDate()));
    }


    public void setContract(HcContract contract) {
        this.contract = contract;
    }

    public HcContract getContract() {
        return contract;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        String token = data.getStringExtra("data");
                        String phoneNumber = data.getStringExtra("phonenumber");
                        MakePaymentRequestValue requestValue = new MakePaymentRequestValue();
                        requestValue.setAmount(String.valueOf(rePaymentData.get().getAmount()));
                        requestValue.setContractNumber(rePaymentData.get().getContractNumber());
                        requestValue.setCustomerNumber(phoneNumber);
                        requestValue.setToken(token);
                        requestValue.setRePaymentId(String.valueOf(rePaymentData.get().getRePaymentId()));
                        requestValue.setProvider("momo");
                        makePaymentForMomo(requestValue);
                    } else {
                        showMessage(R.string.momo_not_receive_info);
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    if (data.getStringExtra("message") != null) {
                        showMessage(data.getStringExtra("message"));
                    } else {
                        showMessage(R.string.momo_payment_failed);
                    }
                } else if (data.getIntExtra("status", -1) == 2) {
                    showMessage(R.string.momo_not_receive_info);
                } else {
                    showMessage(R.string.momo_not_receive_info);
                }
            } else {
                showMessage(R.string.momo_not_receive_info);
            }
        } else {
            showMessage(R.string.momo_not_receive_info_err);
        }
    }

    public void makePaymentForMomo(MakePaymentRequestValue requestValue) {
        setIsLoading(true);
        contractRepository.makePaymentForMomo(requestValue)
                .subscribe(makePaymentResp -> {
                    setIsLoading(false);
                    if (makePaymentResp == null) {
                        return;
                    }
                    if (makePaymentResp.isSuccess()) {
                        makePaymentSuccess(makePaymentResp.getMakePaymentRespData());
                    } else {
                        showMessage(makePaymentResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }

    private void makePaymentSuccess(MakePaymentRespData makePaymentRespData) {
        PaymentSummaryModel model = new PaymentSummaryModel();
        model.setPaymentDate(getCurrentDate());
        model.setBeneficiary(momoSettingInfo != null ? momoSettingInfo.getMerchantName() : "");
        model.setPayerName(preferencesHelper.getProfile().getFullName());
        model.setTotalTransaction(makePaymentRespData.getAmount());
        paymentMomoSuccess.setValue(model);
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c);
    }

    public MutableLiveData<PaymentSummaryModel> getPaymentMomoSuccess() {
        return paymentMomoSuccess;
    }
}
