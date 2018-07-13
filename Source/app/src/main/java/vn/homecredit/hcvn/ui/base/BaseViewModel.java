/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/20/18 3:06 PM  by hien.nguyenm
 */

package vn.homecredit.hcvn.ui.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager mDataManager;
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private final SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private WeakReference<N> mNavigator;
    private MutableLiveData<String> modelErrorMessage = new MutableLiveData<>();
    private MutableLiveData<BaseMessage> mModelBaseMessage = new MutableLiveData<BaseMessage>();

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void startSafeProcess(@NonNull Disposable process){
        mCompositeDisposable.add(process);
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }


    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }


    public N getNavigator() {
        return mNavigator.get();
    }


    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public MutableLiveData<String> getModelErrorMessage() {
        return modelErrorMessage;
    }

    public void setModelErrorMessage(String errorMessage) {
        this.modelErrorMessage.setValue(errorMessage);
    }

    public MutableLiveData<BaseMessage> getModelBaseMessage() {
        return mModelBaseMessage;
    }

    public void sendMessage(BaseMessage message) {
        mModelBaseMessage.setValue(message);
    }

    public void init() {}
}