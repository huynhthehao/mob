package vn.homecredit.hcvn.data.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractType;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.utils.TestData;

public class ContractRepositoryImpl implements ContractRepository {

    private final RestService restService;

    @Inject
    public ContractRepositoryImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<ContractResp> contracts() {
        return restService.contract()
                .map(contractResp -> {
                    if (BuildConfig.DEBUG) {
                        contractResp.getData().getContracts().add(TestData.activeContract());
                        contractResp.getData().getContracts().add(TestData.pendingContract());
                        contractResp.getData().getContracts().add(TestData.pendingContract(ContractType.CashLoan));
                        contractResp.getData().getContracts().add(TestData.pendingContract(ContractType.ConsumerDurables));
                        contractResp.getData().getContracts().add(TestData.activeCreditCardContract());
                        contractResp.getData().getContracts().add(TestData.pendingContract(ContractType.TwoWheels));
                    }
                    return contractResp;
                })
                .map(contractResp -> {
                    List<HcContract> contractList = groupContract(contractResp.getData().getContracts());
                    contractResp.getData().setContracts(contractList);
                   return contractResp;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private List<HcContract> groupContract(List<HcContract> hcContractList) {
        List<HcContract> activeContractList = new ArrayList<>();
        List<HcContract> pendingContractList = new ArrayList<>();
        List<HcContract> closeContractList = new ArrayList<>();
        for (HcContract hcContract : hcContractList) {
            if (hcContract.getTypeStatus() == HcContract.STATUS_ACTIVE) {
                if (activeContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                activeContractList.add(hcContract);
            }else if (hcContract.getTypeStatus() == HcContract.STATUS_PENDING) {
                if (pendingContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                pendingContractList.add(hcContract);
            }else {
                if (closeContractList.size() == 0) {
                    hcContract.setShowSection(true);
                }
                closeContractList.add(hcContract);
            }

        }
        List<HcContract> contractList = new ArrayList<>();
        contractList.addAll(activeContractList);
        contractList.addAll(pendingContractList);
        contractList.addAll(closeContractList);
        return contractList;
    }

}
