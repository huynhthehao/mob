package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.remote.RestService;

public class ContractRepositoryImpl implements ContractRepository {

    private final RestService restService;

    @Inject
    public ContractRepositoryImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<ContractResp> contracts() {
        return restService.contract()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
