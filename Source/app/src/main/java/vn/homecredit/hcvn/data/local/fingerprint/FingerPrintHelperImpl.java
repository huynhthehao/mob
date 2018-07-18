package vn.homecredit.hcvn.data.local.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import javax.inject.Inject;

import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;

public class FingerPrintHelperImpl implements FingerPrintHelper {
    private Context context;

    @Inject
    public FingerPrintHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public FingerPrintAuthValue getFingerPrintAuthValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                // Device doesn't support fingerprint authentication
                return FingerPrintAuthValue.NOT_SUPPORT;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
                return FingerPrintAuthValue.SUPPORT_BUT_NOT_ENABLE;
            } else {
                // Everything is ready for fingerprint authentication
                return FingerPrintAuthValue.SUPPORT_AND_ENABLED;
            }
        }
        return FingerPrintAuthValue.NOT_SUPPORT;
    }
}
