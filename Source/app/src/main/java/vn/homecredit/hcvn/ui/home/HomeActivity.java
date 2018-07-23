/*
 * HomeActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 1:32 PM
 */

package vn.homecredit.hcvn.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.databinding.ActivityHomeBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.setpassword.DialogAnnounceFingerprint;
import vn.homecredit.hcvn.utils.AppUtils;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements DashBoardDialogFragment.OnDashboardClicked, ViewPager.OnPageChangeListener {
    public static final String BUNDLE_SHOW_DASHBOARD = "BUNDLE_SHOW_DASHBOARD";
    public static final String BUNDLE_SHOW_FINGERPRINT = "BUNDLE_SHOW_FINGERPRINT";

    @Inject
    HomeViewModel mHomeViewModel;
    @Inject
    PreferencesHelper preferencesHelper;

    public static void start(Context context, boolean showDashboard ) {
        start(context, showDashboard, false);
    }
    public static void start(Context context) {
        start(context, false, false);
    }
    public static void start(Context context, boolean showDashboard, boolean isShowDialogFingersprint) {
        Intent intent =  new Intent(context, HomeActivity.class);
        intent.putExtra(BUNDLE_SHOW_DASHBOARD , showDashboard);
        intent.putExtra(BUNDLE_SHOW_FINGERPRINT, isShowDialogFingersprint);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        return mHomeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(getViewDataBinding().toolbar);
        getViewModel().setNavigator(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = getViewDataBinding().container;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        TabLayout tabLayout = getViewDataBinding().tabs;
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(this);
        checkToShowDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_dashboard) {
            showDashboard(getString(R.string.hello), mHomeViewModel.getUserName());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkToShowDialog() {
        if (getIntent().getBooleanExtra(BUNDLE_SHOW_DASHBOARD, false) ||
                preferencesHelper.getIsShowDashboard()) {
            showDashboard(getString(R.string.hello), mHomeViewModel.getUserName());
        }
        if (getIntent().getBooleanExtra(BUNDLE_SHOW_FINGERPRINT, false) ) {
            DialogAnnounceFingerprint.showDialog(getSupportFragmentManager());
        }
    }

    private void showDashboard(String greeting, String username) {
        if (getSupportFragmentManager().findFragmentByTag(DashBoardDialogFragment.TAG_DASHBOARD) == null) {
            preferencesHelper.setIsShowDashboard(false);
            DialogFragment dashboardFragment = DashBoardDialogFragment.newInstance(greeting, username);
            ((DashBoardDialogFragment) dashboardFragment).setOnDashboardClicked(this);
            dashboardFragment.show(getSupportFragmentManager(), DashBoardDialogFragment.TAG_DASHBOARD);
        }
    }

    @Override
    public void onClickedContact() {
        mViewPager.setCurrentItem(SectionsPagerAdapter.TAB_CONTRACTS);
    }

    @Override
    public void onClickedSchedule() {
    }

    @Override
    public void onClickedOffer() {
    }

    @Override
    public void onClickedNotification() {
        mViewPager.setCurrentItem(SectionsPagerAdapter.TAB_NOTIFICATION);
    }

    @Override
    public void onClickedMomo() {
        AppUtils.openPlayStoreForApp(this, getString(R.string.momo_app_package));
    }

    @Override
    public void onClickedMore() {
        mViewPager.setCurrentItem(SectionsPagerAdapter.TAB_MORE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case SectionsPagerAdapter.TAB_CONTRACTS:
                setTitle(R.string.contracts);
                break;
            case SectionsPagerAdapter.TAB_NOTIFICATION:
                setTitle(R.string.notifications);
                break;
            case SectionsPagerAdapter.TAB_SUPPORT:
                setTitle(R.string.offers);
                break;
            case SectionsPagerAdapter.TAB_MORE:
                setTitle(R.string.more);
                break;
        }
    }

    @Override
    public void setTitle(int titleId) {
        getViewDataBinding().toolbar.setTitle(titleId);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
