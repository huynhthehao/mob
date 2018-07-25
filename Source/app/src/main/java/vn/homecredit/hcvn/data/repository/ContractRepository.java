package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;

public interface ContractRepository {
    Single<ContractResp> contracts();
}
