package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.remote.RestService;

public class ContractRepositoryImpl implements ContractRepository {

    private final RestService restService;

    @Inject
    public ContractRepositoryImpl(RestService restService) {
        this.restService = restService;
    }

}
