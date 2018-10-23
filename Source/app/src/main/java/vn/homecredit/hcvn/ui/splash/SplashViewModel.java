/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import com.crashlytics.android.Crashlytics;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppLogger;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashListener> {

    private final DataManager dataManager;
    private final OneSignalService mOneSignalService;
    private SplashListener listener;

    @Inject
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, OneSignalService oneSignalService) {
        super(schedulerProvider);
        this.dataManager = dataManager;
        mOneSignalService = oneSignalService;
    }

    public void setListener(SplashListener listener){
        this.listener = listener;
    }

    @Override
    public void init() {
        super.init();
        mOneSignalService.trygetPrivateId();
        checkUpdate();
    }

    public void checkUpdate() {
        setIsLoading(true);
        Disposable newProcess = dataManager.checkUpdate().delay(250, TimeUnit.MILLISECONDS)
                .doOnSuccess(response -> dataManager.setVersionRespData(response.getData()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (dataManager.getAccessToken() != null) {
                        if(listener != null)
                            listener.openHomeActivity();
                    }else {
                        if(listener != null)
                            listener.openWelcomeActivity();
                    }
                }, throwable -> {
                    // TODO: Should use AppLog in develop
                    Crashlytics.logException(AppLogger.createLog(AppLogger.CHECK_UPDATE_FAIL, throwable));
                    setIsLoading(false);
                    if(listener != null)
                        listener.retryCheckUpdate();
                });

        startSafeProcess(newProcess);
    }
}
