package vn.homecredit.hcvn.ui.contract.statement.statementdetails;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.model.StatementDetailModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class StatementDetailsViewModel extends BaseViewModel {
    private final ContractRepository contractRepository;
    private MutableLiveData<Boolean> modelRefreshing = new MutableLiveData<>();
    private MutableLiveData<List<String>> dataStatementDetails = new MutableLiveData<>();
    StatementModel statementModel;
    String contractId = "";

    @Inject
    public StatementDetailsViewModel(SchedulerProvider schedulerProvider, ContractRepository contractRepository) {
        super(schedulerProvider);
        this.contractRepository = contractRepository;
    }

    public void init(String contractId, StatementModel statementModel) {
        this.statementModel = statementModel;
        this.contractId = contractId;
        modelRefreshing.setValue(true);
        refreshData();
    }

    private void refreshData() {
        contractRepository.getStatementDetails(contractId, statementModel)
                .subscribe(statementResp -> {
                    modelRefreshing.setValue(false);
                    if (statementResp == null) {
                        return;
                    }
                    if (statementResp.getResponseCode() != 0) {
                        showMessage(statementResp.getResponseMessage());
                        return;
                    }
                    dataStatementDetails.setValue(getImages(statementResp.getData()));
                }, throwable -> {
                    modelRefreshing.setValue(false);
                    if (throwable instanceof HcApiException) {
                        showMessage(((HcApiException) throwable).getErrorResponseMessage());
                    }

                });
    }

    public MutableLiveData<List<String>> getDataStatementDetails() {
        return dataStatementDetails;
    }

    private List<String> getImages(List<StatementDetailModel> listData) {
        // test
        String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQU3J63zkgb86xW7ejMWk4TcEo0EEcRHX16akchPHmHo4WmW5Ys";
        List<String> testList = new ArrayList<>();
        testList.add(image);
        testList.add(image);
        testList.add(image);
        return testList;
        // end test

        // will open when release
//        List<String> listImages = new ArrayList<>();
//        if (listData == null)
//            return listImages;
//        for (int i = 0; i < listData.size(); i++) {
//            listImages.add(listData.get(i).getImageUrl());
//        }
//        return listImages;
    }
}
