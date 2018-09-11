package vn.homecredit.hcvn.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockApplication;
import android.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DaggerApplication;
import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.service.tracking.TrackingService;

public class BaseSimpleActivity extends AppCompatActivity {
    TrackingService trackingService;

    protected boolean isSupportInjection() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isSupportInjection()) {
            AndroidInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
        trackingService = ((HCVNApp) getApplication()).getTrackingService();
    }

    protected void enableNavigationClicked() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (trackingService != null) {
            trackingService.sendView(this);
        }
    }

}
