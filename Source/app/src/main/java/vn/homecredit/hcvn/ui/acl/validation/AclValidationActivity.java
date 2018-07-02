/*
 * AclValidationPage.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:08 PM
 */

package vn.homecredit.hcvn.ui.acl.validation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityAclValidationBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class AclValidationActivity extends BaseActivity<ActivityAclValidationBinding, AclValidationViewModel> implements AclValidationNavigator {

    @Inject
    AclValidationViewModel mAclValidationViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acl_validation;
    }

    @Override
    public AclValidationViewModel getViewModel() {
        return mAclValidationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acl_validation);
    }
}
