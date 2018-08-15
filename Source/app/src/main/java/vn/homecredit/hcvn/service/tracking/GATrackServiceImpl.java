package vn.homecredit.hcvn.service.tracking;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.R;

@Singleton
public class GATrackServiceImpl implements TrackService {

    private final Context context;
    private GoogleAnalytics googleAnalytics;
    private Tracker tracker;

    @Inject
    public GATrackServiceImpl(Context context) {
        this.context = context;
        googleAnalytics = GoogleAnalytics.getInstance(context);
        googleAnalytics.setDryRun(true);
        tracker = newTracker();
    }

    private Tracker newTracker() {
        Tracker trackerLocal = googleAnalytics.newTracker(getTrackerId());
        if (trackerLocal != null) {
            trackerLocal.enableExceptionReporting(true);
        }
        return trackerLocal;
    }

    protected String getTrackerId() {
        return context.getString(R.string.ga_trackingId);
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        if (tracker == null) return;
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder(category, action);
        eventBuilder.setLabel(label);
        tracker.send(eventBuilder.build());
    }

    @Override
    public void sendView(String screen) {
        if (tracker == null) return;
        tracker.setScreenName(screen);
    }

}
