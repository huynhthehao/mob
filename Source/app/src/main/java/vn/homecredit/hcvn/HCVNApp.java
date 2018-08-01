package vn.homecredit.hcvn;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.homecredit.hcvn.di.component.DaggerAppComponent;
import vn.homecredit.hcvn.di.module.RoomModule;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.utils.AppLogger;
import vn.homecredit.hcvn.utils.Log;

/**
 * Created by QuanP on 11/06/18.
 */

public class HCVNApp extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    OneSignalService oneSignalService;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .roomModule(new RoomModule(this))
                .build()
                .inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(result -> {
                    //TODO: Tracking
                    oneSignalService.notificationOpenHandler(getApplicationContext(), result);
                }).setNotificationReceivedHandler(notification -> {
            Log.debug("value", "full: " + notification.toString());
            Log.debug("value", notification.payload.body);
            //TODO: Tracking

            oneSignalService.notificationReceived(notification);
        }).init();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

}
