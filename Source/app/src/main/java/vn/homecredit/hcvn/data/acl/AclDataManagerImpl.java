package vn.homecredit.hcvn.data.acl;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.acl.ProposeOfferResp;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;
import vn.homecredit.hcvn.utils.rx.AppSchedulerProvider;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Singleton
public class AclDataManagerImpl implements AclDataManager {

    private final ApiHeader mApiHeader;
    private final AclDatabaseService mAclDatabaseService;
    private final AclRestService mAclRestService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public AclDataManagerImpl(ApiHeader apiHeader, AclDatabaseService aclDatabaseService, AclRestService aclRestService) {
        mApiHeader = apiHeader;
        mAclDatabaseService = aclDatabaseService;
        mAclRestService = aclRestService;
        schedulerProvider = new AppSchedulerProvider();
    }

    @Override
    public String getAclAccessToken() {
        return mAclDatabaseService.getAclAccessToken();
    }

    @Override
    public void setAclAccessToken(String aclAccessToken) {
        mAclDatabaseService.setAclAccessToken(aclAccessToken);
        mAclRestService.getApiHeader().getAclApiHeader().setAccessToken(aclAccessToken);
    }

    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        return mAclRestService.verifyPersonal(phone, idNumber, playerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    @Override
    public Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp) {
        return mAclRestService.verifyPersonalOtp(otpPassParam, otp)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    @Override
    public Single<SuggestOfferResp> getSuggestOffer() {
        return mAclRestService.getSuggestOffer();
    }

    @Override
    public Single<ProposeOfferResp> getMonthlyPaymentAsync(double amount, int tenor, float boundScore, String productCode) {
        return mAclRestService.getMonthlyPaymentAsync(amount, tenor, boundScore, productCode);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }
}
