/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:29 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.validation;

import android.widget.Toast;

import com.androidnetworking.error.ANError;

import java.util.HashMap;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclValidationViewModel extends BaseViewModel<AclValidationNavigator> {

    public HashMap<String, String> errorData;
    private AclRuleFactory mAclRuleFactory;

    public AclValidationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclRuleFactory aclRuleFactory) {
        super(dataManager, schedulerProvider);
        mAclRuleFactory = aclRuleFactory;


        errorData = new HashMap<String, String>();
    }

    public void onNextClick()
    {
        getNavigator().next();
    }

    public AclRuleFactory getAclRuleFactory() {
        return mAclRuleFactory;
    }

    public void verifyPersonal(String phoneNumber, String idNumber) {
        setIsLoading(true);
        //TODO: abc TOKEN
        getCompositeDisposable().add(getDataManager()
                .verifyPersonal(phoneNumber, idNumber, "abc")
                .doOnSuccess(response -> {
                    System.out.print(response.toString());
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
//                    getNavigator().openHomeActivity();
//                    System.out.print(response.getAccessToken());
                }, throwable -> {
//                    String t = (((ANError)throwable).getErrorAsObject(OtpTimerResp.class)).getErrorDescription();
//                    getNavigator().showError(t);
                    setIsLoading(false);
                }));
    }
}
