package vn.homecredit.hcvn;

import android.app.Activity;
import android.app.Application;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.interceptors.HttpLoggingInterceptor;
//import com.mindorks.framework.mvvm.di.component.DaggerAppComponent;
//import com.mindorks.framework.mvvm.utils.AppLogger;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.homecredit.hcvn.di.component.DaggerAppComponent;
import vn.homecredit.hcvn.utils.AppLogger;

/**
 * Created by QuanP on 11/06/18.
 */

public class HCVNApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }
}
