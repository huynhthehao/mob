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
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.message.MessageQuestion;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private final SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private WeakReference<N> mNavigator;
    private MutableLiveData<String> messageData = new MutableLiveData<>();
    private MutableLiveData<Integer> messageIdData = new MutableLiveData<>();
    private MutableLiveData<Integer> messageResourceData = new MutableLiveData<>();
    private MutableLiveData<BaseMessage> confirmMessageData = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelReLogin = new MutableLiveData<>();

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

    public MutableLiveData<Integer> getMessageIdData() {
        return messageIdData;
    }

    public MutableLiveData<Integer> getMessageResourceData() {
        return messageResourceData;
    }

    public MutableLiveData<BaseMessage> getConfirmMessageData() {
        return confirmMessageData;
    }

    public MutableLiveData<Boolean> getModelReLogin() {
        return modelReLogin;
    }

    public void showMessage(String message) {
        this.messageData.setValue(message);
    }

    public void showMessage(Integer messageId) {
        this.messageIdData.setValue(messageId);
    }

    public void showMessage(int resId) {
        this.messageResourceData.setValue(resId);
    }

    public void showConfirmMessage(String title, String message, Consumer<Boolean> onCompleted) {
        confirmMessageData.setValue(new MessageQuestion(title, message, onCompleted));
    }

    public void init() {
        modelReLogin.setValue(false);
    }

    public void handleError(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        if (throwable instanceof HcApiException) {
            if (((HcApiException) throwable).getErrorResponseCode() == HcApiException.ERROR_CODE_UNAUTHORIZED) {
                modelReLogin.setValue(true);
            }else {
                if (((HcApiException) throwable).isErrorResponseEmpty()) {
                    messageResourceData.setValue(R.string.error_unexpected);
                }else {
                    messageData.setValue(((HcApiException) throwable).getErrorResponseMessage());
                }
            }
        }else {
            messageData.setValue(throwable.getMessage());
        }

    }
}