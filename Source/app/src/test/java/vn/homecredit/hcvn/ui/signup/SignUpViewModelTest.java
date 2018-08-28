package vn.homecredit.hcvn.ui.signup;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.repository.AccountRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignUpViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    AccountRepository accountRepository;
    SignUpViewModel signUpViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signUpViewModel = new SignUpViewModel(accountRepository, null, null);
    }

    @Test
    public void signUpSuccess() {
        signUpViewModel.username.set("a");
        signUpViewModel.contracts.set("b");

        OtpTimerResp otpTimerResp = new OtpTimerResp();
        otpTimerResp.setResponseCode(0);
        when(accountRepository.signupVerify("a", "b"))
                .thenReturn(Single.just(otpTimerResp));
        signUpViewModel.onClickedSignUp();
        LiveData<OtpPassParam> modelOpt = signUpViewModel.getModelOtpPassParam();

        assertEquals(otpTimerResp, modelOpt.getValue().getOtpTimerResp());
        assertEquals("a", modelOpt.getValue().getPhoneNumber());
        assertEquals("b", modelOpt.getValue().getContractId());
    }

    @Test
    public void signUpFailed() {
        signUpViewModel.username.set("a");
        signUpViewModel.contracts.set("b");

        OtpTimerResp otpTimerResp = new OtpTimerResp();
        otpTimerResp.setResponseCode(-99);
        otpTimerResp.setResponseMessage("Contracts is not exits");

        when(accountRepository.signupVerify("a", "b"))
                .thenReturn(Single.just(otpTimerResp));

        signUpViewModel.onClickedSignUp();
        LiveData<String> modelMessage = signUpViewModel.getMessageData();
        assertEquals(otpTimerResp.getResponseMessage(), modelMessage.getValue());

    }



}