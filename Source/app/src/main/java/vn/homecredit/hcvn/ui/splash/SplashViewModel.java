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

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    private final OneSignalService mOneSignalService;
    @Inject
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, OneSignalService oneSignalService) {
        super(dataManager, schedulerProvider);
        mOneSignalService = oneSignalService;
    }

    public void init() {
        checkUpdate();
            mOneSignalService.tryGetPlayerId();
    }

    public void checkUpdate() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .checkUpdate().delay(250, TimeUnit.MILLISECONDS)
                .doOnSuccess(response -> getDataManager().setVersionRespData(response.getData()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openWelcomeActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().retryCheckUpdate();
                }));
    }
}
