package vn.homecredit.hcvn.ui.contract.detail;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.ResourcesUtil;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.Active;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.Approved;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.Cancelled;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.Finished;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.PaidOff;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.Rejected;
import static vn.homecredit.hcvn.data.model.api.contract.ContractStatus.WrittenOff;

public class ContractDetailViewModel extends BaseViewModel {

    private HcContract hcContract;
    private MutableLiveData<String> modelPaymentSchedule = new MutableLiveData<>();
    private MutableLiveData<String> modelPaymentHistory = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelLocation = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelMomo = new MutableLiveData<>();

    public MutableLiveData<String> getModelStatusColor() {
        return modelStatusColor;
    }

    public void setModelStatusColor(MutableLiveData<String> modelStatusColor) {
        this.modelStatusColor = modelStatusColor;
    }

    private MutableLiveData<String> modelStatusColor = new MutableLiveData<>();

    @Inject
    public ContractDetailViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    @Override
    public void init() {
        super.init();
        modelLocation.setValue(false);
        modelPaymentHistory.setValue(null);
        modelPaymentSchedule.setValue(null);
    }

    public HcContract getHcContract() {
        return hcContract;
    }

    public void setHcContract(HcContract hcContract) {
        this.hcContract = hcContract;
    }

    public MutableLiveData<String> getModelPaymentSchedule() {
        return modelPaymentSchedule;
    }

    public MutableLiveData<String> getModelPaymentHistory() {
        return modelPaymentHistory;
    }

    public MutableLiveData<Boolean> getModelLocation() {
        return modelLocation;
    }

    public MutableLiveData<Boolean> getModelMomo() {
        return modelMomo;
    }

    public void onPaymentScheduleClicked() {
        modelPaymentSchedule.setValue(this.hcContract.getContractNumber());
    }

    public void onPaymentHistoryClicked() {
        modelPaymentHistory.setValue(this.hcContract.getContractNumber());
    }

    public void onLocationClicked() {
        modelLocation.setValue(true);
    }

    public void onMomoClicked() {
        modelMomo.setValue(true);
    }

    public static class ContractViewModel {
        private String clientName;
        private String contractNumber;
        private String signedDate;
        private String productType;
        private Integer tenor;
        private String statusTextVn;
        private String status;

        public ContractViewModel(HcContract hcContract) {
            this.clientName = hcContract.getClientName();
            this.contractNumber = hcContract.getContractNumber();
            this.signedDate = DateUtils.convertDateFromUTCToSimple(hcContract.getSignedDate());
            this.productType = hcContract.getProductType();
            this.tenor = hcContract.getTenor();
            this.statusTextVn = hcContract.getStatusTextVn();
            this.status = hcContract.getStatus();
        }

        public String getClientName() {
            return clientName;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public String getSignedDate() {
            return signedDate;
        }

        public String getProductType() {
            return productType;
        }

        public String getStatusTextVn() {
            return statusTextVn;
        }

        public String getStatus() {
            return status;
        }

        public Integer getTenor() {
            return tenor;
        }


        public int getStatusTextColor() {
            switch (status) {
                case Active:
                    return ResourcesUtil.getColor(R.color.success);
                case Approved:
                case PaidOff:
                case Cancelled:
                case Finished:
                case WrittenOff:
                case Rejected:
                    return ResourcesUtil.getColor(R.color.colorPrimary);
                default:
                    return ResourcesUtil.getColor(R.color.textGray);
            }
        }
    }

}
