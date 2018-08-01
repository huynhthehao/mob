package vn.homecredit.hcvn.ui.contract.detail;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractDetailViewModel extends BaseViewModel {

    private HcContract hcContract;

    @Inject
    public ContractDetailViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public HcContract getHcContract() {
        return hcContract;
    }

    public void setHcContract(HcContract hcContract) {
        this.hcContract = hcContract;
    }

    public static class ContractViewModel {
        private String clientName;
        private String contractNumber;
        private String signedDate;
        private String productType;
        private String formattedTenor;
        private String statusTextVn;

        public ContractViewModel(HcContract hcContract) {
            this.clientName = hcContract.getClientName();
            this.contractNumber = hcContract.getContractNumber();
            this.signedDate = DateUtils.convertDateFromUTCToSimple(hcContract.getSignedDate());
            this.productType = hcContract.getProductType();
            this.formattedTenor = String.format("%d tháng", hcContract.getTenor());
            this.statusTextVn = hcContract.getStatusTextVn();
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

        public String getFormattedTenor() {
            return formattedTenor;
        }

        public String getStatusTextVn() {
            return statusTextVn;
        }
    }
}
