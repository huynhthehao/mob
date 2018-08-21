package vn.homecredit.hcvn.ui.signup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.di.component.AppComponent;
import vn.homecredit.hcvn.di.module.AppModule;
import vn.homecredit.hcvn.util.JUnitDaggerMockRule;
import vn.homecredit.hcvn.util.RobolectricMockTestRule;

import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SignUpViewModelTest {

//    @Rule public DaggerMockRule<AppComponent> mockitoRule = new DaggerMockRule<>(AppComponent.class, new AppModule());
    @Rule public RobolectricMockTestRule rule = new RobolectricMockTestRule();

//    @Rule
//    public TestWatcher schedulerRule = new TrampolineSchedulerRule();
//    @Mock public RestService restService;
//    @Mock public AclRestService aclRestService;
//    @Mock public PayooRestService payooRestService;
//    @Mock public PosRestService posRestService;
//    @Mock public ClwMapService clwMapService;
//    @Mock public DisbursementService disbursementService;
//    @Mock public PaymentService paymentService;
    @Mock AccountRepository accountRepository;

    @InjectFromComponent SignUpViewModel signUpViewModel;

    @Test
    public void test() {
        when(accountRepository.signupVerify("a", "b"))
                .thenReturn(Single.just(new OtpTimerResp()));
        when(signUpViewModel.username.get()).thenReturn("a");
        when(signUpViewModel.contracts.get()).thenReturn("b");

        signUpViewModel.onClickedSignUp();
    }
}