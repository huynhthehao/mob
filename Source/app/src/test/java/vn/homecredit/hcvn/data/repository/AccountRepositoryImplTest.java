package vn.homecredit.hcvn.data.repository;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.RestServiceImpl;
import vn.homecredit.hcvn.database.AppDatabase;
import vn.homecredit.hcvn.di.component.AppComponent;
import vn.homecredit.hcvn.di.module.AppModule;
import vn.homecredit.hcvn.helpers.memory.MemoryHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.service.VersionService;
import vn.homecredit.hcvn.util.ImmediateSchedulerProvider;
import vn.homecredit.hcvn.util.TestSchedulerProvider;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountRepositoryImplTest {
    @Mock RestServiceImpl restService;
    @Mock PreferencesHelper preferencesHelper;
    @Mock ApiHeader apiHeader;
    @Mock OneSignalService oneSignalService;
    @Spy SchedulerProvider schedulerProvider;

    AccountRepositoryImpl accountRepository;
    TestScheduler testScheduler;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        schedulerProvider = new TestSchedulerProvider(testScheduler);
        accountRepository = new AccountRepositoryImpl(restService, preferencesHelper, apiHeader, oneSignalService, null, new ImmediateSchedulerProvider());

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void signupVerify() {
        OtpTimerResp otpTimerResp = new OtpTimerResp();
        otpTimerResp.setResponseCode(0);

        TokenResp tokenResp = new TokenResp();
        tokenResp.responseCode = 0;
        tokenResp.setAccessToken("accesstoken");

        ProfileResp profileResp = new ProfileResp();
        profileResp.setResponseCode(0);
        profileResp.setResponseMessage("aaa");

        ProfileResp.ProfileRespData profileRespData = new ProfileResp.ProfileRespData();
        profileRespData.setUserName("aaa");
        profileRespData.setFullName("fullname");
        profileResp.setData(profileRespData);


        when(restService.getToken("aa", "bb"))
                .thenReturn(Single.just(tokenResp));

        when(restService.getProfile())
                .thenReturn(Single.just(profileResp));

        TestObserver<ProfileResp> testObserver = accountRepository.signIn("aa", "bb").test();
        testObserver.assertValue(profileResp);

        verify(preferencesHelper).setAccessToken("accesstoken");
        verify(preferencesHelper).saveProfile(profileRespData);
    }
}