/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    private final DataManager dataManager;
    private final OneSignalService mOneSignalService;
    @Inject
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, OneSignalService oneSignalService) {
        super(schedulerProvider);
        this.dataManager = dataManager;
        mOneSignalService = oneSignalService;
    }

    @Override
    public void init() {
        super.init();
        mOneSignalService.tryGetPlayerId();
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
                        getNavigator().openHomeActivity();
                    }else {
                        getNavigator().openWelcomeActivity();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().retryCheckUpdate();
                });

        startSafeProcess(newProcess);
    }
}
