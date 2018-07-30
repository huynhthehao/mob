package vn.homecredit.hcvn.ui.contract.signing;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SigningViewModel extends BaseViewModel {

    private ContractRepository contractRepository;

    @Inject
    public SigningViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }
    public void setContractsId(String contractsId) {
        contractRepository.masterContract(contractsId)
                .subscribe(new Consumer<MasterContract>() {
                    @Override
                    public void accept(MasterContract masterContract) throws Exception {

                    }
                });
    }
}
