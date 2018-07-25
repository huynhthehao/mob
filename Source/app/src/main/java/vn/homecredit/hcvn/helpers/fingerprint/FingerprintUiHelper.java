/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/23/18 1:09 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.helpers.fingerprint;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.custom.BouncingInterpolator;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintUiHelper extends FingerprintManager.AuthenticationCallback {

    private static final long SUCCESS_DELAY_MILLIS = 700;

    private final FingerprintManager mFingerprintManager;
    private final ImageView mIcon;
    private final TextView messageTextView;
    private final Callback mCallback;
    private CancellationSignal mCancellationSignal;
    private Context context;

    private boolean mSelfCancelled;

    public FingerPrintUiHelper(FingerprintManager fingerprintManager, Context context,
                               ImageView icon, TextView messageTextView, Callback callback) {
        this.context = context;
        mFingerprintManager = fingerprintManager;
        mIcon = icon;
        this.messageTextView = messageTextView;
        mCallback = callback;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isFingerprintAuthAvailable() {
        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {
            return;
        }
        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        mFingerprintManager.authenticate(cryptoObject, mCancellationSignal, 0 /* flags */, this, null);
        mIcon.setImageResource(R.drawable.ic_finger_print_red);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!mSelfCancelled) {
            showError(errString);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        //showError(helpString);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationFailed() {
        Animation shake = AnimationUtils.loadAnimation(this.context, R.anim.shake);
        mIcon.startAnimation(shake);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        messageTextView.removeCallbacks(mResetErrorTextRunnable);
        mIcon.setColorFilter(context.getResources().getColor(R.color.success), PorterDuff.Mode.SRC_IN);

        startBouncing();
        mIcon.postDelayed(() -> mCallback.onAuthenticated(), SUCCESS_DELAY_MILLIS);
    }


    public void startBouncing(){
        Animation bouncingAni = AnimationUtils.loadAnimation(this.context, R.anim.bounce);
        BouncingInterpolator interpolator = new BouncingInterpolator(0.2, 20);
        bouncingAni.setInterpolator(interpolator);
        mIcon.startAnimation(bouncingAni);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showError(CharSequence error) {
        System.out.println(error);

        messageTextView.setText(context.getResources().getString(R.string.fingerprint_validation_error));
        messageTextView.setTextColor(context.getResources().getColor(R.color.warning_color, null));
        messageTextView.removeCallbacks(mResetErrorTextRunnable);
    }

    public void refreshMessage(){
        messageTextView.setText(context.getResources().getString(R.string.fingerprint_description));
        messageTextView.setTextColor(context.getResources().getColor(R.color.brownishGrey, null));
    }

    private Runnable mResetErrorTextRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            messageTextView.setTextColor(context.getResources().getColor(R.color.hint_color, null));
            messageTextView.setText(context.getResources().getString(R.string.fingerprint_hint));
            mIcon.setImageResource(R.drawable.ic_finger_print_red);
        }
    };

    public interface Callback {
        void onAuthenticated();
        void onError();
    }
}
