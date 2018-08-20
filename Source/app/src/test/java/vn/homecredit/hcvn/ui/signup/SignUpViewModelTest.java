package vn.homecredit.hcvn.ui.signup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.reactivex.Single;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.RestServiceImpl;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;
import vn.homecredit.hcvn.data.remote.acl.AclRestServiceImpl;
import vn.homecredit.hcvn.data.remote.clwmap.ClwMapService;
import vn.homecredit.hcvn.data.remote.clwmap.ClwMapServiceImpl;
import vn.homecredit.hcvn.data.remote.disbursement.DisbursementService;
import vn.homecredit.hcvn.data.remote.disbursement.DisbursementServiceImpl;
import vn.homecredit.hcvn.data.remote.payment.PaymentService;
import vn.homecredit.hcvn.data.remote.payment.PaymentServiceImpl;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestService;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestServiceImpl;
import vn.homecredit.hcvn.data.remote.pos.PosRestService;
import vn.homecredit.hcvn.data.remote.pos.PosRestServiceImpl;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.di.component.AppComponent;
import vn.homecredit.hcvn.di.module.AppModule;
import vn.homecredit.hcvn.util.TrampolineSchedulerRule;
import vn.homecredit.hcvn.utils.AppUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignUpViewModelTest {

    @Rule
    public DaggerMockRule<AppComponent> mockitoRule = new DaggerMockRule<>(AppComponent.class, new AppModule());
    @Rule
    public TestWatcher schedulerRule = new TrampolineSchedulerRule();
    @Mock public RestService restService;
    @Mock public AclRestService aclRestService;
    @Mock public PayooRestService payooRestService;
    @Mock public PosRestService posRestService;
    @Mock public ClwMapService clwMapService;
    @Mock public DisbursementService disbursementService;
    @Mock public PaymentService paymentService;

    @Mock AccountRepository accountRepository;
    @InjectFromComponent SignUpViewModel signUpViewModel;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        when(accountRepository.signupVerify("a", "b"))
                .thenReturn(Single.just(new OtpTimerResp()));
        when(signUpViewModel.username.get()).thenReturn("a");
        when(signUpViewModel.contracts.get()).thenReturn("b");

        signUpViewModel.onClickedSignUp();
    }
}