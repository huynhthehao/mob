package vn.homecredit.hcvn;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.onesignal.OneSignal;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.homecredit.hcvn.di.component.AppComponent;
import vn.homecredit.hcvn.di.component.DaggerAppComponent;
import vn.homecredit.hcvn.di.module.RoomModule;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.AppLogger;

/**
 * Created by QuanP on 11/06/18.
 */

public class HCVNApp extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    private static Context context;
    private AppComponent appComponent;

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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .roomModule(new RoomModule(this))
                .build();
        appComponent.inject(this);

        context = getApplicationContext();

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
            //TODO: Tracking
            oneSignalService.notificationReceived(getApplicationContext(), notification);
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

    public static Context getContext() {
        return context;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
