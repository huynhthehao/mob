/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/16/18 4:56 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.login;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.LoginInformation;
import vn.homecredit.hcvn.databinding.ActivityLoginBinding;
import vn.homecredit.hcvn.helpers.CryptoHelper;
import vn.homecredit.hcvn.helpers.prefs.AppPreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.FingerprintAuthenticationDialogFragment;
import vn.homecredit.hcvn.ui.forgetpassword.ForgetPasswordActivity;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.signup.SignUpActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private static final String DEFAULT_KEY_NAME = "default_key";
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Inject
    LoginViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return viewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.setNavigator(this);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
        initKeyInfo();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initKeyInfo(){
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        createKey(DEFAULT_KEY_NAME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();

        if(viewModel.fingerPrintEnable()){
            showFingerPrintAuthDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustBottom();
    }

    public void openHomeActivity() {
        HomeActivity.start(LoginActivity.this, true);
        finish();
    }

    @Override
    public void forgetPassword() {
        ForgetPasswordActivity.start(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void showFingerPrintAuthDialog() {
        try {
            Cipher mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);

            if(!initCipher(mCipher, DEFAULT_KEY_NAME))
                return;

            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            FingerprintAuthenticationDialogFragment fragment = new FingerprintAuthenticationDialogFragment(fingerprintManager);

            fragment.setOnValidateSuccess(this::onValidatedSuccess);
            fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
            fragment.show(getFragmentManager(), "FingerPrintDialog");
        }catch(Exception ex){
            showMessage(ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onValidatedSuccess(){
        //showMessage("success");
        viewModel.autoLogin();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createKey(String keyName) {
        try {
            keyStore.load(null);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (Exception e) {
            showMessage("Failed to init Cipher");
            return false;
        }
    }

    private void adjustBottom() {
        View bottomGroupView = getViewDataBinding().bottomGroupView;
        View scrollView = getViewDataBinding().scrollView;
        View topGroupView = getViewDataBinding().topGroupView;

        int marginTop = scrollView.getHeight() - topGroupView.getHeight() - bottomGroupView.getHeight();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, marginTop, 0, 0);
        bottomGroupView.setLayoutParams(lp);
    }
}

