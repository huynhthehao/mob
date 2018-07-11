package vn.homecredit.hcvn.data.acl;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;

@Singleton
public class AclDataManagerImpl implements AclDataManager {

    private final AclRestService mAclRestService;

    @Inject
    public AclDataManagerImpl(AclRestService aclRestService) {
        mAclRestService = aclRestService;
    }

    @Override
    public String getAclAccessToken() {
        return null;
    }

    @Override
    public void setAclAccessToken(String aclAccessToken) {

    }

    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        return mAclRestService.verifyPersonal(phone, idNumber, playerId);
    }

    @Override
    public Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp) {
        return mAclRestService.verifyPersonalOtp(otpPassParam, otp);
    }
}
