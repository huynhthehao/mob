package vn.homecredit.hcvn.ui.notification;

import vn.homecredit.hcvn.R;

public enum NotificationType {
    INCOMING(0, R.drawable.ic_receipt),
    REMINDER(1, R.drawable.ic_payment_reminder),
    CRM(2, R.drawable.ic_loan_offer),
    UPDATE(3, R.drawable.ic_update),
    SIGN_UP_WAIT(4, R.drawable.ic_news),
    MARKETING(5, R.drawable.ic_news),
    CLW_APPLICATION_LOAN_RESULT(6, R.drawable.ic_news),
    CLW_APPLICATION_LOAN_EXPIRED(7, R.drawable.ic_news),
    CLW_APPLICATION_LOAN_REMIND_SIGNING(8, R.drawable.ic_news),
    SECURITY(9, R.drawable.ic_security);

    private final int type;
    private final int resouceIconId;

    NotificationType(int type, int resouceIconId) {
        this.type = type;
        this.resouceIconId = resouceIconId;
    }

    public int getType() {
        return type;
    }

    public int getResouceIconId() {
        return resouceIconId;
    }

    public static int getResouceIconIdForUpdate(boolean isNeedUpdate) {
        if (isNeedUpdate) {
            return R.drawable.ic_update;
        } else {
            return R.drawable.ic_opening;
        }
    }

    public static int getImageResourceIdByType(int type) {
        for (NotificationType value : NotificationType.values()) {
            if (value.getType() == type) {
                return value.getResouceIconId();
            }
        }
        return MARKETING.getResouceIconId();
    }

    public static NotificationType getNotificationByType(int type) {
        for (NotificationType value : NotificationType.values()) {
            if (value.getType() == type) {
                return value;
            }
        }
        return INCOMING;
    }
}
