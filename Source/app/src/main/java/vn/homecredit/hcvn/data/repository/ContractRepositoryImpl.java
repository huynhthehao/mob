package vn.homecredit.hcvn.data.repository;

import android.os.Debug;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
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
                        contractResp.getData().getContracts().add(TestData.approvedContract());
                        contractResp.getData().getContracts().add(TestData.pendingContract());
                    }
                    return contractResp;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
