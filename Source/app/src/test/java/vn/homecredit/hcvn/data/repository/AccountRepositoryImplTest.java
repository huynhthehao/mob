package vn.homecredit.hcvn.data.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
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
import vn.homecredit.hcvn.util.TestSchedulerProvider;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountRepositoryImplTest {

//    @Rule public final DaggerMockRule<AppComponent> rule = new DaggerMockRule<>(AppComponent.class, new AppModule());

    @Rule public final MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock PreferencesHelper preferencesHelper;
    @Mock ApiHeader apiHeader;
    @Mock OneSignalService oneSignalService;
    @Mock AppDatabase appDatabase;
    @Mock MemoryHelper memoryHelper;
    @Mock DeviceInfo deviceInfo;
    @Mock VersionService versionService;
    @Mock RestServiceImpl restService;
    @Mock TestSchedulerProvider schedulerProvider;
    @InjectMocks
    AccountRepositoryImpl accountRepository;

    TestScheduler testScheduler;



    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
//        schedulerProvider = new TestSchedulerProvider(testScheduler);
//        accountRepository = new AccountRepositoryImpl(restService, preferencesHelper, apiHeader, oneSignalService, appDatabase, schedulerProvider);
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
        when(schedulerProvider.io()).thenReturn(testScheduler);
        when(schedulerProvider.ui()).thenReturn(testScheduler);

        when(restService.getToken("aa", "bb"))
                .thenReturn(Single.just(tokenResp));

        when(restService.getProfile())
                .thenReturn(Single.just(profileResp));

        TestObserver<ProfileResp> testObserver = accountRepository.signIn("aa", "bb").test();
//        testObserver.assertOf(profileRespTestObserver -> verify(preferencesHelper).setAccessToken("accesstoken"));
        testObserver.assertValue(profileResp);
    }
}