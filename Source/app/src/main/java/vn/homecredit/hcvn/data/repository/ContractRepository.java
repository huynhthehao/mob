package vn.homecredit.hcvn.data.repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.PaymentResp;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;

public interface ContractRepository {
    Single<ContractResp> contracts();
    Single<MasterContractResp> masterContract(String contractId);
    Single<MasterContractDocResp> masterContractDoc(String contractId);
    Observable<MasterContractResp> startPrepare(String contractId);
    Single<OtpTimerResp> masterContractApproved(String contractId);
    Single<OtpTimerResp> masterContractVerify(String contractId, String otp,  boolean hasDisbursementBankAccount, boolean isCreditCardContract);
    Single<ScheduleDetailResp> viewInstalmentsv1(String contractId);
    Single<PaymentResp> viewPaymentsv1(String contractId);
}
