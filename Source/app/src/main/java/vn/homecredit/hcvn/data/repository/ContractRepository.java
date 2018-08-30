package vn.homecredit.hcvn.data.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHistoryResp;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementResp;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.model.StatementDetailsResp;

public interface ContractRepository {
    Single<ContractResp> contracts();

    Single<ContractResp> activeContracts();

    Single<MasterContractResp> masterContract(String contractId);

    Single<MasterContractDocResp> masterContractDoc(String contractId);

    Observable<MasterContractResp> startPrepare(String contractId);

    Single<OtpTimerResp> masterContractApproved(String contractId);

    Single<MasterContractVerifyResp> masterContractVerify(String contractId, String otp, boolean hasDisbursementBankAccount, boolean isCreditCardContract);

    Observable<Boolean> checkMasterContractVerified(String contractId, int timeout, int interval);

    Single<ScheduleDetailResp> viewInstalmentsv1(String contractId);

    Single<PaymentHistoryResp> viewPaymentsv1(String contractId);

    Single<StatementResp> getStatements(String contractId);

    Single<StatementDetailsResp> getStatementDetails(String contractId, StatementModel statementModel);

    Single<RePaymentResp> getRePayment(String contractId);
}
