package vn.homecredit.hcvn.ui.login;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Single;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelperImpl;
import vn.homecredit.hcvn.mock.MockData;
import vn.homecredit.hcvn.ui.base.TestBaseViewModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginViewModelTest extends TestBaseViewModel {

    @Mock
    AccountRepository accountRepository;
    @Mock FingerPrintHelper fingerPrintHelper;

    LoginViewModel loginViewModel;

    @Override
    public void initData() {
        viewModel = new LoginViewModel(accountRepository, null, fingerPrintHelper, null, null);
        loginViewModel = (LoginViewModel) viewModel;
    }

    @Test
    public void loginFailed(){
        loginViewModel.username.set("username");
        loginViewModel.password.set("");
        loginViewModel.onLoginClick();
        assertMessage(R.string.login_invalid_input);

        loginViewModel.username.set("");
        loginViewModel.password.set("password");
        loginViewModel.onLoginClick();
        assertMessage(R.string.login_invalid_input);

    }

    @Test
    public void loginSuccess() {

        String username = "username";
        String password = "password";
        loginViewModel.username.set(username);
        loginViewModel.password.set(password);
        ProfileResp profileResp = MockData.profileResp(0, null);
        when(accountRepository.signIn(username, password))
                .thenReturn(Single.just(profileResp));
        loginViewModel.onLoginClick();
        verify(accountRepository).saveLoginInfo(username, password);


    }
}