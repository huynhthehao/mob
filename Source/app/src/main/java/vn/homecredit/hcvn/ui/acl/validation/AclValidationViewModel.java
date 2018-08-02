/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 3:22 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.acl.validation;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclValidationViewModel extends AclBaseViewModel<AclValidationNavigator> {

    private AclRuleFactory mAclRuleFactory;
    private final DeviceInfo mDeviceInfo;

    @Inject
    public AclValidationViewModel(SchedulerProvider schedulerProvider, AclRuleFactory aclRuleFactory, DeviceInfo deviceInfo, AclDataManager aclDataManager) {
        super(schedulerProvider, aclDataManager, 1);
        mAclRuleFactory = aclRuleFactory;
        mDeviceInfo = deviceInfo;
    }


    public void onNextClick() {
        getNavigator().next();
    }

    public AclRuleFactory getAclRuleFactory() {
        return mAclRuleFactory;
    }

    public void verifyPersonal(String phoneNumber, String idNumber) {
        setIsLoading(true);
        Disposable newProcess = getAclDataManager()
                .verifyPersonal(phoneNumber, idNumber, mDeviceInfo.getPlayerId())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response.isVerified()) {
                        getNavigator().openOtpActivity(new OtpPassParam(response, phoneNumber, idNumber, OtpFlow.CASH_LOAN_WALKIN));
                    } else {
                        getNavigator().showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    handleError(throwable);
                    setIsLoading(false);
                });

        startSafeProcess(newProcess);
    }
}
