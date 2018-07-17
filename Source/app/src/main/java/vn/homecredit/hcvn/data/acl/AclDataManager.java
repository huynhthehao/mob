package vn.homecredit.hcvn.data.acl;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.acl.ProposeOfferResp;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;

public interface AclDataManager {
    Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId);
    Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp);
    Single<SuggestOfferResp> getSuggestOffer();
    Single<ProposeOfferResp> getMonthlyPaymentAsync(double amount, int tenor, float boundScore, String productCode);

    String getAclAccessToken();
    void setAclAccessToken(String aclAccessToken);
}
