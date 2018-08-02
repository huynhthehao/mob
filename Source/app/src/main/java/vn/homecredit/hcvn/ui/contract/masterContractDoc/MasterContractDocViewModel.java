package vn.homecredit.hcvn.ui.contract.masterContractDoc;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.ObservableString;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class MasterContractDocViewModel extends BaseViewModel {

    private ContractRepository contractRepository;
    private MasterContract masterContract;
    private MutableLiveData<List<String>> modelMasterContractsDocs = new MutableLiveData<>();
    private MutableLiveData<OtpPassParam> modelOtpPassParam = new MutableLiveData<>();

    @Inject
    public MasterContractDocViewModel(ContractRepository contractRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;
        getMasterContractDoc(masterContract.getContractNumber());
    }

    public MutableLiveData<OtpPassParam> getModelOtpPassParam() {
        return modelOtpPassParam;
    }

    public MutableLiveData<List<String>> getModelMasterContractsDocs() {
        return modelMasterContractsDocs;
    }

    public void getMasterContractDoc(String contractId) {
        setIsLoading(true);
        contractRepository.masterContractDoc(contractId)
                .subscribe(masterContractDocResp -> {
                            setIsLoading(false);
                            modelMasterContractsDocs.setValue(masterContractDocResp.getImages());
                        },
                        throwable -> {
                            setIsLoading(false);
                            handleError(throwable);
                        });
    }

    public void doApprove() {
        setIsLoading(true);
        contractRepository.masterContractApproved(masterContract.getContractNumber())
                .subscribe(otpTimerResp -> {
                    setIsLoading(false);
                    processOtpTimer(otpTimerResp);
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }

    private void processOtpTimer(OtpTimerResp otpTimerResp) {
        if (otpTimerResp == null) {
            return;
        }
        if (otpTimerResp.isVerified()) {
            OtpPassParam otpPassParam = new OtpPassParam(otpTimerResp, masterContract);
            modelOtpPassParam.setValue(otpPassParam);
        }else {
            showMessage(otpTimerResp.getResponseMessage());
        }
    }


}
