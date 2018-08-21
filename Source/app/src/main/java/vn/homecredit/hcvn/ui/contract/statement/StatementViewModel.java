package vn.homecredit.hcvn.ui.contract.statement;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class StatementViewModel extends BaseViewModel {
    private final ContractRepository contractRepository;
    private String contractId = "";
    private MutableLiveData<Boolean> modelRefreshing = new MutableLiveData<>();
    private MutableLiveData<List<StatementModel>> dataStatements = new MutableLiveData<>();

    @Inject
    public StatementViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void initData(String contractId) {
        this.contractId = contractId;
        modelRefreshing.setValue(true);
        refreshData();
    }

    public void pullToRefresh() {
        refreshData();
    }

    private void refreshData() {
        contractRepository.getStatements(contractId)
                .subscribe(statementResp -> {
                    modelRefreshing.setValue(false);
                    if (statementResp == null) {
                        return;
                    }
                    if (statementResp.getResponseCode() != 0) {
                        showMessage(statementResp.getResponseMessage());
                        return;
                    }
                    dataStatements.setValue(statementResp.getData());
                }, throwable -> {
                    modelRefreshing.setValue(false);
                    handleError(throwable);
                });
    }

    public MutableLiveData<List<StatementModel>> getDataStatements() {
        return dataStatements;
    }

    public MutableLiveData<Boolean> getModelRefreshing() {
        return modelRefreshing;
    }
}
