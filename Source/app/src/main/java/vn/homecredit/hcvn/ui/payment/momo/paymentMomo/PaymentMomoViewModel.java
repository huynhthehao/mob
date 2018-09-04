package vn.homecredit.hcvn.ui.payment.momo.paymentMomo;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.TestData;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PaymentMomoViewModel extends BaseViewModel {

    private ObservableField<RePaymentData> rePaymentData = new ObservableField<>();
    private ContractRepository contractRepository;
    private HcContract contract;
    private ObservableField<Boolean> fullPayment = new ObservableField<>(true);
    private ObservableField<Boolean> partialPayment = new ObservableField<>(false);
    private MutableLiveData<Boolean> modelPaymentViaMomo = new MutableLiveData<>();

    @Inject
    public PaymentMomoViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    @Override
    public void init() {
        super.init();
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
        Log.debug("momo" + fullPayment.get() + "," + partialPayment.get());
        modelPaymentViaMomo.setValue(true);
    }

    private void getRepayment() {
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


    // TODO: Need to refresh layout base on init data
    public void initData(boolean hasRepayment, HcContract contract, RePaymentData rePaymentIntent){
        if(hasRepayment){
            rePaymentData.set(rePaymentIntent);
            return;
        }

        this.contract = contract;
        getRepayment();
    }

    public HcContract getContract() {
        return contract;
    }
}
