/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/9/18 4:55 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityAclApplicationFormBinding;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan.AclAfSelectLoanFragment;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class AclApplicationFormActivity extends BaseActivity<ActivityAclApplicationFormBinding, AclApplicationFormViewModel>
implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {
        return new Intent(context, AclApplicationFormActivity.class);
    }

    @Inject
    AclApplicationFormViewModel mAclApplicationFormViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acl_application_form;
    }

    @Override
    public AclApplicationFormViewModel getViewModel() {
        return mAclApplicationFormViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(getViewDataBinding().toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFrame, new AclAfSelectLoanFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acl_af, menu);
        return true;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
