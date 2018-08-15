package vn.homecredit.hcvn.service.tracking;

public interface TrackService {
    void sendEvent(String category, String action, String label);
    void sendView(String screen);
}
