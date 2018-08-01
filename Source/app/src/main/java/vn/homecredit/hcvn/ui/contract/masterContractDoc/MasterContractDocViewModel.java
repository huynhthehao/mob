package vn.homecredit.hcvn.ui.contract.masterContractDoc;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractDocViewModel extends BaseViewModel {

    private ContractRepository contractRepository;
    private MasterContract masterContract;
    private MutableLiveData<List<String>> modelMasterContractsDocs = new MutableLiveData<>();

    @Inject
    public MasterContractDocViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;
        getMasterContractDoc(masterContract.getContractNumber());
    }

    public MutableLiveData<List<String>> getModelMasterContractsDocs() {
        return modelMasterContractsDocs;
    }

    public void getMasterContractDoc(String contractId) {
        contractRepository.masterContractDoc(contractId)
                .subscribe(masterContractDocResp -> modelMasterContractsDocs.setValue(masterContractDocResp.getImages()),
                        throwable -> handleError(throwable));
    }


}
