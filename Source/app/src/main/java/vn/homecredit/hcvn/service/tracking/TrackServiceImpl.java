package vn.homecredit.hcvn.service.tracking;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrackServiceImpl implements TrackService {

    private final TrackService gaTrackServiceImpl;
    private final TrackService fbTrackService;

    @Inject
    public TrackServiceImpl(TrackService gaTrackServiceImpl, TrackService fbTrackService){
        this.gaTrackServiceImpl = gaTrackServiceImpl;
        this.fbTrackService = fbTrackService;
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        gaTrackServiceImpl.sendEvent(category, action, label);
        fbTrackService.sendEvent(category, action, label);

    }

    @Override
    public void sendView(String screen) {
        gaTrackServiceImpl.sendView(screen);
        fbTrackService.sendView(screen);
    }

}
