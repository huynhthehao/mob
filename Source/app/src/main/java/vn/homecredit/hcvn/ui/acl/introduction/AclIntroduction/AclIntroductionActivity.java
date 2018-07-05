/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:15 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityAclIntroductionBinding;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragment;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeNavigator;
import vn.homecredit.hcvn.ui.acl.introduction.SectionsPagerAdapter;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class AclIntroductionActivity extends BaseActivity<ActivityAclIntroductionBinding, AclIntroductionViewModel> implements AclSelectLoanTypeNavigator, HasSupportFragmentInjector, AclSelectLoanTypeFragment.OnFragmentInteractionListener {

    @Inject
    AclIntroductionViewModel mAclIntroductionViewModel;
    private ActivityAclIntroductionBinding mActivityAclIntroductionBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {
        return new Intent(context, AclIntroductionActivity.class);
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acl_introduction;
    }

    @Override
    public AclIntroductionViewModel getViewModel() {
        return mAclIntroductionViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSelectACLO() {
        Intent intent = AclValidationActivity.newIntent(AclIntroductionActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSelectPOS() {

    }

    @Override
    public void goACLO() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
