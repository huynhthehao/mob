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
public class FingerPrintUIHelper extends FingerprintManager.AuthenticationCallback {

    private static final long ERROR_TIMEOUT_MILLIS = 1600;
    private static final long SUCCESS_DELAY_MILLIS = 700;

    private final FingerprintManager mFingerprintManager;
    private final ImageView mIcon;
    private final TextView mErrorTextView;
    private final Callback mCallback;
    private CancellationSignal mCancellationSignal;
    private Context context;

    private boolean mSelfCancelled;

    public FingerPrintUIHelper(FingerprintManager fingerprintManager, Context context,
                               ImageView icon, TextView errorTextView, Callback callback) {
        this.context = context;
        mFingerprintManager = fingerprintManager;
        mIcon = icon;
        mErrorTextView = errorTextView;
        mCallback = callback;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isFingerprintAuthAvailable() {
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
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
            mIcon.postDelayed(() -> mCallback.onError(), ERROR_TIMEOUT_MILLIS);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        showError(helpString);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationFailed() {
        Animation shake = AnimationUtils.loadAnimation(this.context, R.anim.shake);
        mIcon.startAnimation(shake);

        showError(mIcon.getResources().getString(R.string.fingerprint_not_recognized));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        mErrorTextView.removeCallbacks(mResetErrorTextRunnable);
        mIcon.setColorFilter(context.getResources().getColor(R.color.success), PorterDuff.Mode.SRC_IN);
        mErrorTextView.setTextColor(mErrorTextView.getResources().getColor(R.color.success_color, null));
        mErrorTextView.setText(mErrorTextView.getResources().getString(R.string.fingerprint_success));

        Animation bouncingAni = AnimationUtils.loadAnimation(this.context, R.anim.bounce);
        BouncingInterpolator interpolator = new BouncingInterpolator(0.2, 20);
        bouncingAni.setInterpolator(interpolator);
        mIcon.startAnimation(bouncingAni);
        mIcon.postDelayed(() -> mCallback.onAuthenticated(), SUCCESS_DELAY_MILLIS);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showError(CharSequence error) {
        mErrorTextView.setText(error);
        mErrorTextView.setTextColor(
                mErrorTextView.getResources().getColor(R.color.warning_color, null));
        mErrorTextView.removeCallbacks(mResetErrorTextRunnable);
        mErrorTextView.postDelayed(mResetErrorTextRunnable, ERROR_TIMEOUT_MILLIS);
    }

    private Runnable mResetErrorTextRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            mErrorTextView.setTextColor(
                    mErrorTextView.getResources().getColor(R.color.hint_color, null));
            mErrorTextView.setText(
                    mErrorTextView.getResources().getString(R.string.fingerprint_hint));
            mIcon.setImageResource(R.drawable.ic_finger_print_red);
        }
    };

    public interface Callback {
        void onAuthenticated();
        void onError();
    }
}
