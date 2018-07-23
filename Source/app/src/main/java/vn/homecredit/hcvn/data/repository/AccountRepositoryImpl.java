package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
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

    @Override
    public Single<OtpTimerResp> forgotPasswordVerify(String phone, String contractsId) {
        return restService.forgetPasswordVerify(phone, contractsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<OtpTimerResp> forgotPasswordOtp(String phone, String contractsId, String otp) {
        return restService.forgetPasswordOTP(phone, contractsId, otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ProfileResp> forgotPasswordSetNew(String phone, String contractsId, String otp, String password) {
        return restService.forgetPasswordSetNew(phone, contractsId, otp, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
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
                    apiHeader.getProtectedApiHeader().setAccessToken(preferencesHelper.getAccessToken());
                })
                .flatMap((Function<TokenResp, SingleSource<ProfileResp>>) tokenResp -> getProfileWithoutSubscribeOn())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ProfileResp> signUpThenLogin(String phone, String contractsId, String otp, String password) {
        return restService.signUp(phone, contractsId, otp, password)
                .flatMap(tokenResp -> restService.getToken(phone, password)
                        .doOnSuccess(tokenResp1 -> {
                            preferencesHelper.setAccessToken(tokenResp1.getAccessToken());
                            apiHeader.getProtectedApiHeader().setAccessToken(preferencesHelper.getAccessToken());

                        })
                ).flatMap((Function<TokenResp, SingleSource<ProfileResp>>) tokenResp -> getProfileWithoutSubscribeOn())
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

    @Override
    public void updatePassword(String password) {
        preferencesHelper.updatePassword(password);
    }


}
