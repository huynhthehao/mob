/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 12:51 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.data.model.message.MessageQuestion;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private final SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private WeakReference<N> mNavigator;
    private MutableLiveData<String> messageData = new MutableLiveData<>();
    private MutableLiveData<BaseMessage> confirmMessageData = new MutableLiveData<>();

    public BaseViewModel(SchedulerProvider schedulerProvider) {
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

    public MutableLiveData<String> getMessageData() {
        return messageData;
    }

    public MutableLiveData<BaseMessage> getConfirmMessageData() {
        return confirmMessageData;
    }

    public void showMessage(String errorMessage) {
        this.messageData.setValue(errorMessage);
    }

    public void showConfirmMessage(String title, String message, Consumer<Boolean> onCompleted) {
        confirmMessageData.setValue(new MessageQuestion(title, message, onCompleted));
    }

    public void init() {}
}