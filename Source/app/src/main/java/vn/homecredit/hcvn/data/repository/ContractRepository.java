package vn.homecredit.hcvn.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;

public interface ContractRepository {
    Single<ContractResp> contracts();
    Single<MasterContractResp> masterContract(String contractId);
    Single<MasterContractDocResp> masterContractDoc(String contractId);
    Observable<MasterContractResp> startPrepare(String contractId);
}
