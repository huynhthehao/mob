/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.remote;

import java.util.List;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHistoryResp;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionResp;
import vn.homecredit.hcvn.data.model.api.support.SupportHistoryResp;
import vn.homecredit.hcvn.data.model.api.support.SupportResp;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;
import vn.homecredit.hcvn.data.model.offer.ContractOfferResp;
import vn.homecredit.hcvn.data.model.offer.OfferDetailResp;
import vn.homecredit.hcvn.data.model.tracking.InternalTrackingModel;
import vn.homecredit.hcvn.data.model.tracking.TrackingResp;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementResp;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.model.StatementDetailsResp;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentRequestValue;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentResp;

public interface RestService extends RestUrl {
    //Account
    Single<VersionResp> checkUpdate();

    Single<TokenResp> getToken(String phoneNumber, String password);

    Single<ProfileResp> getProfile();

    Single<OtpTimerResp> verifySignupOTP(String phone, String idNumber, String otp);

    ApiHeader getApiHeader();

    Single<OtpTimerResp> verified(String username, String contractsId);

    Single<OtpTimerResp> changePassword(String oldPassword, String newPassword);

    Single<OtpTimerResp> forgetPasswordVerify(String phone, String contractId);

    Single<OtpTimerResp> forgetPasswordOTP(String phone, String contractId, String otp);

    Single<ProfileResp> signUp(String phone, String contracsId, String otp, String password);

    Single<ProfileResp> forgetPasswordSetNew(String phone, String contracsId, String otp, String password);

    Single<NotificationResp> getNotifications();

    Single<BaseApiResponse> markNotificationAsRead(String notificationId);

    //Contracts
    Single<TransactionResp> getTransactions(String contractId, boolean isHold, boolean isRepay, int withinMonths);

    Single<ContractResp> contract();

    Single<MasterContractResp> masterContract(String contractId);

    Single<MasterContractDocResp> masterContractDoc(String contractId);

    Single<OtpTimerResp> masterContractApprove(String contractId);

    Single<ScheduleDetailResp> viewInstalmentsv1(String contractId);

    Single<PaymentHistoryResp> viewPaymentsv1(String contractId);

    Single<StatementResp> getStatements(String contractId);

    Single<StatementDetailsResp> getStatementDetails(String contractId, StatementModel statementModel);

    Single<MasterContractVerifyResp> masterContractVerify(String contractId, String otp, boolean hasDisbursementBankAccount, boolean isCreditCardContract);

    Single<RePaymentResp> getRePayment(String contractId);

    Single<MakePaymentResp> makePaymentForMomo(MakePaymentRequestValue requestValue);

    //Support
    Single<SupportResp> submitFeedback(String subject, String description, String phoneNumber, String contractId);

    Single<SupportHistoryResp> getSupportHistories();

    Single<ClwModel> getClwNear(Double lat, Double lon);

    Single<DisbursementModel> getDisbursementNear(Double lat, Double lon);

    Single<PaymentModel> getPaymenttNear(Double lat, Double lon);

    //Offer
    Single<ContractOfferResp> contractOffer(String campId);

    Single<OfferDetailResp> offerFormula(String riskGroup, String productCode);

    //Internal Tracking
    Single<TrackingResp> trackingAuthenticated(InternalTrackingModel data);

    Single<TrackingResp> trackingAnonymous(InternalTrackingModel data);


}
