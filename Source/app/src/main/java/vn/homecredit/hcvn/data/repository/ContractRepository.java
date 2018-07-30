package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;

public interface ContractRepository {
    Single<ContractResp> contracts();
    Single<MasterContract> masterContract(String contractId);
}
