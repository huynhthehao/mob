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

    private final AclDatabaseService mAclDatabaseService;
    private final AclRestService mAclRestService;

    @Inject
    public AclDataManagerImpl(AclDatabaseService aclDatabaseService, AclRestService aclRestService) {
        mAclDatabaseService = aclDatabaseService;
        mAclRestService = aclRestService;
    }

    @Override
    public String getAclAccessToken() {
        return mAclDatabaseService.getAclAccessToken();
    }

    @Override
    public void setAclAccessToken(String aclAccessToken) {
        mAclDatabaseService.setAclAccessToken(aclAccessToken);
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
