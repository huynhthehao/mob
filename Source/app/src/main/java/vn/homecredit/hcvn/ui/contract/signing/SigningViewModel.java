package vn.homecredit.hcvn.ui.contract.signing;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Html;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.custom.ContractSummaryItem;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SigningViewModel extends BaseViewModel {

    private ContractRepository contractRepository;

    private ObservableField<String> customerName = new ObservableField<>("");
    private ObservableField<String> contractNumber = new ObservableField<>("");
    private ObservableField<Integer> totalLoanAmount = new ObservableField<>();
    private ObservableField<String> interestRate = new ObservableField<>("");
    private ObservableField<Boolean> insurance = new ObservableField<>(false);
    private ObservableField<String> paymentDuration = new ObservableField<>("");
    private ObservableField<Integer> monthlyInstallement = new ObservableField<>();
    private ObservableField<String> status = new ObservableField<>("");
    private ObservableField<String> bankName = new ObservableField<>("");
    private ObservableField<String> bankAccount = new ObservableField<>("");
    private ObservableField<String> bankBranch = new ObservableField<>("");
    private ObservableField<Boolean> hasDisbursementBankAccount = new ObservableField<>(false);
    private ObservableField<Integer> btnNextText = new ObservableField<>(R.string.master_contract_approved);
    private ObservableField<Boolean> isPreparing = new ObservableField<>(false);
    private MasterContract masterContract;
    private MutableLiveData<Boolean> modelViewDoc = new MutableLiveData<>();

    @Inject
    public SigningViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    @Override
    public void init() {
        super.init();
        modelViewDoc.setValue(false);
    }

    public MutableLiveData<Boolean> getModelViewDoc() {
        return modelViewDoc;
    }

    public ObservableField<Integer> getBtnNextText() {
        return btnNextText;
    }

    public ObservableField<Boolean> getHasDisbursementBankAccount() {
        return hasDisbursementBankAccount;
    }

    public ObservableField<Boolean> getIsPreparing() {
        return isPreparing;
    }

    public ObservableField<String> getCustomerName() {
        return customerName;
    }

    public ObservableField<String> getContractNumber() {
        return contractNumber;
    }

    public ObservableField<Integer> getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public ObservableField<String> getInterestRate() {
        return interestRate;
    }

    public ObservableField<Boolean> getInsurance() {
        return insurance;
    }

    public ObservableField<String> getPaymentDuration() {
        return paymentDuration;
    }

    public ObservableField<Integer> getMonthlyInstallement() {
        return monthlyInstallement;
    }

    public ObservableField<String> getStatus() {
        return status;
    }

    public ObservableField<String> getBankName() {
        return bankName;
    }

    public ObservableField<String> getBankAccount() {
        return bankAccount;
    }

    public ObservableField<String> getBankBranch() {
        return bankBranch;
    }

    public MasterContract getMasterContract() {
        return masterContract;
    }

    public void onNextClicked() {
        if (masterContract == null) {
            return;
        }
        if (masterContract.isMaterialPrepared()) {
            // TODO: 8/1/2018 Load Master Contract Doc
        }else {
//            prepare();
            modelViewDoc.setValue(true);
        }
    }

    private void prepare() {
        isPreparing.set(true);
        contractRepository.startPrepare(masterContract.getContractNumber())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(masterContractResp -> {
                            Log.debug("" + masterContractResp.getMasterContract().isMaterialPrepared());
                            isPreparing.set(false);
                            updateData(masterContractResp.getMasterContract());
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            isPreparing.set(false);
                            showMessage(R.string.master_contract_approve_retry);
                        });
    }

    public void setContractsId(String contractsId) {
        setIsLoading(true);
        contractRepository.masterContract(contractsId)
                .subscribe(masterContract -> {
                    setIsLoading(false);
                    if (masterContract == null) {
                        return;
                    }
                    updateData(masterContract.getMasterContract());
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }

    private void updateData(MasterContract masterContract) {
        this.masterContract = masterContract;
        if (masterContract == null) return;
        customerName.set(masterContract.getCustomerFullname());
        contractNumber.set(masterContract.getContractNumber());
        totalLoanAmount.set(masterContract.getLoanAmount());
        interestRate.set(masterContract.getPresentedInterestRate());
        paymentDuration.set(masterContract.getPaymentDuration());
        monthlyInstallement.set(masterContract.getTotalMonthlyPayment());
        status.set(masterContract.getContractStatusText());
        hasDisbursementBankAccount.set(masterContract.isHasDisbursementBankAccount());
        bankName.set(masterContract.getDisbursementBankName());
        bankAccount.set(masterContract.getDisbursementBankAccountNumber());
        bankBranch.set(masterContract.getDisbursementBankBranchname());
        btnNextText.set(masterContract.isMaterialPrepared() ? R.string.master_contract_approved : R.string.master_contract_prepare_data);
        insurance.set(masterContract.isHasInsurance());
    }

    @BindingAdapter({"interest"})
    public static void setInterest(ContractSummaryItem contractSummaryItem, String interest) {
        contractSummaryItem.setContent(contractSummaryItem.getContext().getString(R.string.contract_summary_interest, interest));
    }

    @BindingAdapter({"currency"})
    public static void setCurrency(ContractSummaryItem contractSummaryItem, Integer loanAmount) {
        if (loanAmount == null) return;
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(loanAmount);
        contractSummaryItem.setContent(Html.fromHtml(contractSummaryItem.getContext().getString(R.string.currency, formattedNumber)));
    }

}
