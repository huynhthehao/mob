/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 1:05 PM, by Admin
 */

package vn.homecredit.hcvn.ui.payment.momo.payothers;

import android.databinding.ObservableField;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppConstants;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PayOthersViewModel extends BaseViewModel {

    private final RestService restService;
    private final PreferencesHelper preferencesHelper;
    private PayOthersListener listener;

    public ObservableField<String> contractNumber = new ObservableField("");

    @Inject
    public PayOthersViewModel(SchedulerProvider schedulerProvider, RestService restService, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
    }

    public void setListener(PayOthersListener listener){
        this.listener = listener;
    }

    public void onNextTapped() {
        if(StringUtils.isNullOrWhiteSpace(contractNumber.get())){
            showMessage(R.string.paymomo_others_contract_null);
            return;
        }

        // TODO: This is a work-around stuff. Need to implement at the server side
        if(contractNumber.get().length() != AppConstants.CONTRACT_NUMBER_LENGTH){
            showMessage(R.string.paymomo_others_contract_wrong_length);
            return;
        }

        if(listener == null)
            return;

        doRepayment();
    }

    public void onContractNumberHelpTapped(){
        if(listener == null)
            return;

        String supportPhoneNumber = preferencesHelper.getVersionRespData().getCustomerSupportPhone();
        if(StringUtils.isNullOrWhiteSpace(supportPhoneNumber))
            supportPhoneNumber = AppConstants.SUPPORT_PHONE;

        listener.onContractHelp(supportPhoneNumber);
    }

    private void doRepayment() {
        setIsLoading(true);
        restService.getRePayment(contractNumber.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rePaymentResp -> {
                    setIsLoading(false);
                    if (rePaymentResp == null) {
                        return;
                    }

                    if (rePaymentResp.isSuccess()) {
                        RePaymentData data = rePaymentResp.getRePaymentData();
                        // update to hide total amount and customerId in case pay for others
                        data.setTotalAmount(0);
                        data.setIdNumber("");
                        // end update to hide total amount and customerId
                        listener.onNext(data);
                    }else {
                        showMessage(rePaymentResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }
}
