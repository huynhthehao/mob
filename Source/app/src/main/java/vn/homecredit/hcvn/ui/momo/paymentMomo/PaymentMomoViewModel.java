package vn.homecredit.hcvn.ui.momo.paymentMomo;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.custom.PayMomoInfoItem;
import vn.homecredit.hcvn.utils.TestData;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PaymentMomoViewModel extends BaseViewModel {

    private ObservableField<RePaymentData> rePaymentData = new ObservableField<>();
    private ContractRepository contractRepository;
    private HcContract contract;

    @Inject
    public PaymentMomoViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    @Override
    public void init() {
        super.init();
    }

    public ObservableField<RePaymentData> getRePaymentData() {
        return rePaymentData;
    }

    public void payViaMomoClicked() {

    }

    public void getRepayment() {
        if (contract == null || contract.getContractNumber() == null) {
            return;
        }
        setIsLoading(true);
        contractRepository.getRePayment(contract.getContractNumber())
                .subscribe(rePaymentResp -> {
                    setIsLoading(false);
                    if (rePaymentResp == null) {
                        return;
                    }
                    if (rePaymentResp.isSuccess()) {
                        rePaymentData.set(rePaymentResp.getRePaymentData());
                    }else {
                        showMessage(rePaymentResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                    if (BuildConfig.DEBUG) {
                        rePaymentData.set(TestData.rePaymentResp().getRePaymentData());
                    }
                });
    }


    public void setContract(HcContract contract) {
        this.contract = contract;
    }

    public HcContract getContract() {
        return contract;
    }



    @BindingAdapter({"amount"})
    public static void setLoanAmount(PayMomoInfoItem payMomoInfoItem, Integer loanAmount) {
        if (loanAmount == null) return;
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(loanAmount);
        payMomoInfoItem.setContent(formattedNumber);
    }
}
