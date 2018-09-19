package vn.homecredit.hcvn.helpers.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import com.samsung.android.sdk.SsdkVendorCheck;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import javax.inject.Inject;

import vn.homecredit.hcvn.utils.FingerPrintAuthValue;

public class FingerPrintHelperImpl implements FingerPrintHelper {
    private Context context;

    @Inject
    public FingerPrintHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public FingerPrintAuthValue getFingerPrintAuthValue() {
        if (SsdkVendorCheck.isSamsungDevice())
            return getSamsungFingerPrintAuthValue();

        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return FingerPrintAuthValue.NOT_SUPPORT;
    }


    private FingerPrintAuthValue getSamsungFingerPrintAuthValue() {
        try {
            Spass spass = new Spass();
            spass.initialize(context);
            SpassFingerprint spassFingerprint = new SpassFingerprint(context);

            if (!spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT))
                return FingerPrintAuthValue.NOT_SUPPORT;

            if (!spassFingerprint.hasRegisteredFinger())
                return FingerPrintAuthValue.SUPPORT_BUT_NOT_ENABLE;

        } catch (Exception e) {
            return FingerPrintAuthValue.NOT_SUPPORT;
        }
        return FingerPrintAuthValue.SUPPORT_AND_ENABLED;
    }
}
