/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:29 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.validation;

import java.util.HashMap;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclValidationViewModel extends AclBaseViewModel<AclValidationNavigator> {

    public HashMap<String, String> errorData;
    private AclRuleFactory mAclRuleFactory;
    private final DeviceInfo mDeviceInfo;

    @Inject
    public AclValidationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclRuleFactory aclRuleFactory, DeviceInfo deviceInfo, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider, aclDataManager);
        mAclRuleFactory = aclRuleFactory;
        mDeviceInfo = deviceInfo;

        errorData = new HashMap<String, String>();
    }

    public void onNextClick() {
        getNavigator().next();
    }

    public AclRuleFactory getAclRuleFactory() {
        return mAclRuleFactory;
    }

    public void verifyPersonal(String phoneNumber, String idNumber) {
        setIsLoading(true);
        getCompositeDisposable().add(getAclDataManager()
                .verifyPersonal(phoneNumber, idNumber, mDeviceInfo.getPlayerId())
                .doOnSuccess(response -> {
                    System.out.print(response.toString());
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {

                    setIsLoading(false);
                    if (response.getResponseCode() == 0 || response.getResponseCode() == 64) {
                        getNavigator().openOtpActivity(new OtpPassParam(response, phoneNumber, idNumber, OtpFlow.CashLoanWalkin));
                    } else {
                        getNavigator().showError(response.getResponseMessage());
                    }
                }, throwable -> {
                    String t = throwable.getMessage();
                    getNavigator().showError(t);
                    setIsLoading(false);
                }));
    }
}
