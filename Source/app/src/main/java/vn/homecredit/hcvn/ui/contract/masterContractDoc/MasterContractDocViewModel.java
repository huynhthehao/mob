package vn.homecredit.hcvn.ui.contract.masterContractDoc;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractDocViewModel extends BaseViewModel {

    private ContractRepository contractRepository;
    private MasterContract masterContract;

    public MasterContractDocViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;
    }

    public void getMasterContractDoc(String contractId) {

        contractRepository.masterContractDoc(contractId).subscribe(new Consumer<MasterContractDocResp>() {
            @Override
            public void accept(MasterContractDocResp masterContractDocResp) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }


}
