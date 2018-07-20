package vn.homecredit.hcvn.data.account;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.OneSignalService;

public class AccountRepositoryImpl implements AccountRepository {

    private final RestService restService;
    private final PreferencesHelper preferencesHelper;
    private final ApiHeader apiHeader;
    private OneSignalService oneSignalService;

    @Inject
    public AccountRepositoryImpl(RestService restService, PreferencesHelper preferencesHelper, ApiHeader apiHeader, OneSignalService oneSignalService) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
        this.apiHeader = apiHeader;
        this.oneSignalService = oneSignalService;
    }

    @Override
    public Single<OtpTimerResp> signupVerify(String username, String contractsId) {
        return restService.verified(username, contractsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<OtpTimerResp> changePassword(String password, String newPassword) {
        return restService.changePassword(password, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) ;
    }

    public Single<OtpTimerResp> verifyOtpSignUp(String phone, String contractsId, String otp) {
        return restService.verifySignupOTP(phone, contractsId, otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<ProfileResp> signUp(String phone, String contractsId, String otp, String password) {
        return restService.signUp(phone, contractsId, otp, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<ProfileResp> signIn(String phoneNumber, String password) {
        return restService.getToken(phoneNumber, password)
                .doOnSuccess(tokenResp -> {
                    preferencesHelper.setAccessToken(tokenResp.getAccessToken());
                })
                .flatMap((Function<TokenResp, SingleSource<ProfileResp>>) tokenResp -> getProfileWithoutSubscribeOn())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ProfileResp> getProfile() {
        return getProfileWithoutSubscribeOn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<ProfileResp> getProfileWithoutSubscribeOn() {
        apiHeader.getProtectedApiHeader().setAccessToken(preferencesHelper.getAccessToken());
        return restService.getProfile()
                .doOnSuccess(response -> {
                    if (response.getResponseCode() == 0) {
                        // cache profile resp
                        preferencesHelper.saveProfile(response.getData());

                        // push notification config
                        oneSignalService.SendTags("UserId", response.getData().getUserId());
                        oneSignalService.SendTags("UserName", response.getData().getFullName());
                        //TODO: Notifcation Setting
                        //                mOneSignalService.SendTags("Active", Settings.Notification.ToString());
                        //TODO: Set Badge
                        //                App.Current.SetBadge(resp.Data.NotificationCount);
                    }
                });
    }

    @Override
    public ProfileResp.ProfileRespData getCachedProfile() {
        return preferencesHelper.loadProfile();
    }

    public void updatePassword(String password) {
        preferencesHelper.updatePassword(password);
    }


}
