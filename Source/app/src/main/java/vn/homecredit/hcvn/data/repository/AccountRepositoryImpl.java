package vn.homecredit.hcvn.data.repository;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.LoginInformation;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.database.AppDatabase;
import vn.homecredit.hcvn.helpers.CryptoHelper;
import vn.homecredit.hcvn.helpers.prefs.AppPreferencesHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.utils.AppUtils;

public class AccountRepositoryImpl implements AccountRepository {

    private final RestService restService;
    private final PreferencesHelper preferencesHelper;
    private final ApiHeader apiHeader;
    private OneSignalService oneSignalService;
    private AppDatabase appDatabase;

    @Inject
    public AccountRepositoryImpl(RestService restService, PreferencesHelper preferencesHelper, ApiHeader apiHeader, OneSignalService oneSignalService, AppDatabase appDatabase) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
        this.apiHeader = apiHeader;
        this.oneSignalService = oneSignalService;
        this.appDatabase = appDatabase;
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
                .observeOn(AndroidSchedulers.mainThread());
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
                        oneSignalService.sendTags("UserId", response.getData().getUserId());
                        oneSignalService.sendTags("UserName", response.getData().getFullName());
                        oneSignalService.sendTags("Active", String.valueOf(preferencesHelper.getNotificationSetting()));
                        //TODO: Notifcation Setting
                        //                mOneSignalService.sendTags("Active", Settings.Notification.ToString());
                        //TODO: Set Badge
                        //                App.Current.SetBadge(resp.Data.NotificationCount);
                    }
                });
    }

    @Override
    public ProfileResp.ProfileRespData getCachedProfile() {
        return preferencesHelper.getProfile();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updatePassword(String password) {
        String currentUser = preferencesHelper.getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_User, String.class);
        String key = AppPreferencesHelper.PREF_KEY_LOGGED_ON_INFO + currentUser;
        LoginInformation loginInformation = new LoginInformation(currentUser, password);
        String encryptData = CryptoHelper.encryptObject(loginInformation);
        preferencesHelper.saveObject(key, encryptData);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void saveLoginInfo(String phoneNumber, String password) {
        preferencesHelper.saveObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_User, phoneNumber);
        preferencesHelper.setTimeLogin(Calendar.getInstance().getTimeInMillis());
        updatePassword(password);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginInformation getCurrentLoginInfo() {
        String currentUser = preferencesHelper.getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_User, String.class);
        String key = AppPreferencesHelper.PREF_KEY_LOGGED_ON_INFO + currentUser;
        String savedLoginInfo = preferencesHelper.getObject(key, String.class);
        return CryptoHelper.decryptObject(savedLoginInfo, LoginInformation.class);
    }

    public String getCurrentUser() {
        return preferencesHelper.getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_User, String.class);
    }

    @Override
    public boolean isExpired() {
        long lastLoginTime = preferencesHelper.getTimeLogin();
        if (lastLoginTime == 0) return true;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        return currentTime - lastLoginTime > 5 * DateUtils.MINUTE_IN_MILLIS;
    }

    @Override
    public void logout(Context context) {
        preferencesHelper.logout();
        // delete tags notification
        oneSignalService.deleteTag("UserId");
        oneSignalService.deleteTag("UserName");

        // delete all tables in database
        Thread t = new Thread(() -> appDatabase.clearAllTables());
        t.start();

        // clear all current push notifications on status bar
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();

        // restart app
        AppUtils.restartApp(context);
    }
}
