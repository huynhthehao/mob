/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:15 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityAclIntroductionBinding;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragment;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeNavigator;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;

public class AclIntroductionActivity extends BaseActivity<ActivityAclIntroductionBinding, AclIntroductionViewModel> implements AclSelectLoanTypeNavigator, HasSupportFragmentInjector, AclSelectLoanTypeFragment.OnFragmentInteractionListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    @Inject
    AclIntroductionViewModel mAclIntroductionViewModel;
    private ActivityAclIntroductionBinding mActivityAclIntroductionBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {
        return new Intent(context, AclIntroductionActivity.class);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;

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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = getViewDataBinding().container;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        getViewDataBinding().tabLayoutDots.setupWithViewPager(mViewPager, true);
        getViewDataBinding().tvActionBack.setOnClickListener(this);
        getViewDataBinding().tvActionNext.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            previous();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onSelectACLO() {
        AclValidationActivity.start(this);
    }

    @Override
    public void onSelectPOS() {
        PayMapActivity.start(this, PayMapActivity.CASH_LOAN_MODE);
        finish();
    }

    @Override
    public void goACLO() {

    }

    @Override
    public void goPos() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvActionBack:
                previous();
                break;
            case R.id.tvActionNext:
                next();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Do nothing
    }

    @Override
    public void onPageSelected(int position) {
        /*if (position == mSectionsPagerAdapter.getCount() - 1) {
            hideNextButton();
        } else {
            showNextButton();
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Do nothing
    }

    private void next() {
        int lastPageIndex = mViewPager.getAdapter().getCount() - 1;

        if(mViewPager.getCurrentItem() != lastPageIndex)
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        else{
            onSelectPOS();
        }
    }

    private void previous() {
        if (mViewPager.getCurrentItem() == 0) {
            onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    private void hideNextButton() {
        if (getViewDataBinding().tvActionNext.getVisibility() != View.GONE) {
            getViewDataBinding().tvActionNext.setVisibility(View.GONE);
        }
    }

    private void showNextButton() {
        if (getViewDataBinding().tvActionNext.getVisibility() != View.VISIBLE) {
            getViewDataBinding().tvActionNext.setVisibility(View.VISIBLE);
        }
    }
}
