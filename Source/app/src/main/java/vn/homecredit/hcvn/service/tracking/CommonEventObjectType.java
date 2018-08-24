package vn.homecredit.hcvn.service.tracking;

public enum CommonEventObjectType {
    NOTIFICATION("Notification");

    private String value;
    CommonEventObjectType(String objectType) {
        this.value = objectType;
    }

    public String getValue() {
        return value;
    }
}
