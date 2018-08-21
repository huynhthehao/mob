package vn.homecredit.hcvn.service.tracking;

public enum CommonEventAction {
    NOTIFICATION_RECEIVED("Notification Received"),
    NOTIFICATION_OPENED("Notification Opened");

    private String value;

    CommonEventAction(String eventAction) {
        this.value = eventAction;
    }

    public String getValue() {
        return value;
    }
}
