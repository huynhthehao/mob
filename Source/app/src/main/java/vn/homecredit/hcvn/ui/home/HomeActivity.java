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
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.android.databinding.library.baseAdapters.BR;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.enums.FirstComeFlow;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.databinding.ActivityHomeBinding;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.ActionDialogFragment;
import vn.homecredit.hcvn.ui.momo.MomoForTestActivity;
import vn.homecredit.hcvn.ui.notification.NotificationsFragment;
import vn.homecredit.hcvn.ui.settings.SettingsActivity;
import vn.homecredit.hcvn.utils.SpanBuilder;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements DashBoardDialogFragment.OnDashboardClicked, ViewPager.OnPageChangeListener, NotificationsFragment.OnNotificationCountListener {
    public static final String BUNDLE_SHOW_DASHBOARD = "BUNDLE_SHOW_DASHBOARD";
    public static final String BUNDLE_SELECT_NOTIFICATION_TAB = "BUNDLE_SELECT_NOTIFICATION_TAB";
    DialogFragment dashboardFragment;
    public static final String BUNDLE_SHOW_FIRSTCOME = "BUNDLE_SHOW_FIRSTCOME";

    @Inject
    HomeViewModel homeViewModel;

    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    NotificationRepository notificationRepository;

    public static void start(Context context, boolean showDashboard) {
        start(context, showDashboard, FirstComeFlow.AFTER_LOGIN);
    }

    public static void start(Context context) {
        start(context, false, FirstComeFlow.AFTER_LOGIN);
    }

    public static void start(Context context, boolean showDashboard, FirstComeFlow firstComeFlow) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(BUNDLE_SHOW_DASHBOARD, showDashboard);
        intent.putExtra(BUNDLE_SHOW_FIRSTCOME, firstComeFlow);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;

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
        return homeViewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // force set locale flow the app setting, for get relative time span in notification list.
        String languageToLoad = preferencesHelper.getLanguageCode(); // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        // end set locale

        setSupportActionBar(getViewDataBinding().toolbar);
        getViewModel().setNavigator(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = getViewDataBinding().container;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        tabLayout = getViewDataBinding().tabs;
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(this);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
        }
        checkToShowDialog();
        checkToOpenNotificationTab(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkToOpenNotificationTab(intent);
    }

    private void checkToOpenNotificationTab(Intent intent) {
        if (intent.getBooleanExtra(BUNDLE_SELECT_NOTIFICATION_TAB, false)) {
            openNotificationTabFromPushNotification();
        }
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
            showDashboard(getString(R.string.hello), homeViewModel.getUserName());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkToShowDialog() {

        boolean key1 = getIntent().getBooleanExtra(BUNDLE_SHOW_DASHBOARD, false);
        boolean key2 = preferencesHelper.getIsShowDashboard();
        if (key1 || key2) {
            showDashboard(getString(R.string.hello), homeViewModel.getUserName());
        }

        if (!homeViewModel.canShowFingerprintGuiding())
            return;

        int bundleValue = getIntent().getIntExtra(BUNDLE_SHOW_FIRSTCOME, FirstComeFlow.AFTER_LOGIN.getValue());
        FirstComeFlow firstComeFlow = FirstComeFlow.parse(bundleValue);
        ActionDialogFragment.showDialog(getFragmentManager(),
                getFirstComeDialogContent(firstComeFlow),
                R.string.setting_goto,
                R.drawable.ic_finger_print_red,
                () -> {
                    Intent intent = SettingsActivity.newIntent(getBaseContext());
                    startActivity(intent);
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private CharSequence getFirstComeDialogContent(FirstComeFlow firstComeFlow) {
        SpanBuilder spanBuilder = new SpanBuilder();

        String contentDefault = getString(R.string.fingerprint_firstime);
        if (firstComeFlow == FirstComeFlow.AFTER_SIGNUP)
            contentDefault = getString(R.string.fingerprint_firstime_after_sigup);

        spanBuilder.appendWithSpace(contentDefault);
        spanBuilder.append(getString(R.string.fingerprint_title), new StyleSpan(android.graphics.Typeface.BOLD));
        spanBuilder.append(getString(R.string.fingerprint_firstime_end));

        return spanBuilder.build();
    }


    private void showDashboard(String greeting, String username) {
        if (getSupportFragmentManager().findFragmentByTag(DashBoardDialogFragment.TAG_DASHBOARD) == null) {
            preferencesHelper.setIsShowDashboard(false);
            dashboardFragment = DashBoardDialogFragment.newInstance(greeting, username);
            ((DashBoardDialogFragment) dashboardFragment).setOnDashboardClicked(this);
            dashboardFragment.show(getSupportFragmentManager(), DashBoardDialogFragment.TAG_DASHBOARD);
            // update notification count
            notificationRepository.getCachedNotifications()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(notificationModels -> {
                        if (notificationModels != null) {
                            int count = 0;
                            for (int i = 0; i < notificationModels.size(); i++) {
                                if (!notificationModels.get(i).isRead()) {
                                    count = count + 1;
                                }
                            }
                            updateNotificationCountForDashBoard(count);
                        }
                    });
        }
    }

    @Override
    public void onClickedContact() {
        setViewPagerCurrentItemWithoutSmoothScroll(SectionsPagerAdapter.TAB_CONTRACTS);
    }

    @Override
    public void onClickedSchedule() {
    }

    @Override
    public void onClickedOffer() {
    }

    @Override
    public void onClickedNotification() {
        setViewPagerCurrentItemWithoutSmoothScroll(SectionsPagerAdapter.TAB_NOTIFICATION);
    }

    @Override
    public void onClickedMore() {
        setViewPagerCurrentItemWithoutSmoothScroll(SectionsPagerAdapter.TAB_MORE);
    }

    private void setViewPagerCurrentItemWithoutSmoothScroll(int tab) {
        mViewPager.setCurrentItem(tab, false);
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
                setTitle(R.string.feedback);
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

    private void openNotificationTabFromPushNotification() {
        dismissDashBoardAndFingerPrintDialog();
        setViewPagerCurrentItemWithoutSmoothScroll(SectionsPagerAdapter.TAB_NOTIFICATION);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + mViewPager.getCurrentItem());
        if (mViewPager.getCurrentItem() == 1 && page != null) {
            ((NotificationsFragment) page).refreshList();
        }
    }

    private void dismissDashBoardAndFingerPrintDialog() {
        if (dashboardFragment != null) {
            dashboardFragment.dismiss();
        }
    }

    @Override
    public void updateNotificationCount(int count) {
        if (mSectionsPagerAdapter == null)
            return;
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        mSectionsPagerAdapter.updateNotificationCount(tab.getCustomView(), count);

        // update dashboard if dashboard is opening, and receive a push notification.
        updateNotificationCountForDashBoard(count);
    }

    private void updateNotificationCountForDashBoard(int count) {
        if (dashboardFragment == null) {
            return;
        }
        ((DashBoardDialogFragment) dashboardFragment).updateNotificationCount(count);
    }

    @Override
    public void onClickedMomo() {
        Intent intent = new Intent(this, MomoForTestActivity.class);
        startActivity(intent);
    }

}
