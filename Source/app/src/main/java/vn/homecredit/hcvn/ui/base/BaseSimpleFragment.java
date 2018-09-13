package vn.homecredit.hcvn.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.AndroidSupportInjection;
import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.service.tracking.TrackingService;

public abstract class BaseSimpleFragment extends Fragment {

    TrackingService trackingService;
    public abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSupportInjection()) {
            AndroidSupportInjection.inject(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        trackingService = ((HCVNApp) getActivity().getApplication()).getTrackingService();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isDisableTracking()) {
            if (trackingService != null) {
                trackingService.sendView(this);
            }
        }
    }

    protected boolean isSupportInjection() {
        return false;
    }
    protected boolean isDisableTracking() {
        return false;
    }
}
