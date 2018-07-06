/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void Init() {
        CheckUpdate();
    }

    public void CheckUpdate() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .CheckUpdate().delay(250, TimeUnit.MILLISECONDS)
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
