package vn.homecredit.hcvn.ui.notification.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.ui.notification.NotificationType;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.offers.OfferActivity;
import vn.homecredit.hcvn.utils.CountryValue;

@Singleton
public class NotificationManagerImpl implements NotificationManager {
    private PreferencesHelper preferencesHelper;
    private NotificationRepository notificationRepository;

    @Inject
    public NotificationManagerImpl(PreferencesHelper preferencesHelper, NotificationRepository notificationRepository) {
        this.preferencesHelper = preferencesHelper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void openNotification(Context context, NotificationModel model) {
        markAsReadNotificationToServer(model);
        NotificationType notificationType = NotificationType.getNotificationByType(model.getType());
        switch (notificationType) {
            case CRM:
                OfferActivity.startFromNotificationOnStatusBar(context, model.getOffer());
                break;
            case INCOMING:
            case REMINDER:
            case MARKETING:
//                AppUtils.openExternalBrowser(context, getMarketingUrl(model));
//                break;
            case CLW_APPLICATION_LOAN_EXPIRED:
            case CLW_APPLICATION_LOAN_RESULT:
            case CLW_APPLICATION_LOAN_REMIND_SIGNING:
                openDefaultActivity(context);
                break;
        }
    }

    private void openDefaultActivity(Context context) {
        if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return;
        }
        HomeActivity.startAndOpenNotificationScreen(context, preferencesHelper);
    }

    private String getMarketingUrl(NotificationModel model) {
        if (preferencesHelper.getLanguageCode().equalsIgnoreCase(CountryValue.VIETNAMESE.getLanguageCode())) {
            return model.getMarketingUrlVi();
        } else {
            return model.getMarketingUrlEn();
        }
    }

    private void markAsReadNotificationToServer(NotificationModel model) {
        notificationRepository.markNotificationAsRead(model.getId())
                .subscribe(
                        baseResp ->
                        {
                            if (baseResp.getResponseCode() == 0) {
                                updateNotificationAsRead(model.getId());
                            }
                        },
                        throwable -> {
                        });
    }

    private void updateNotificationAsRead(String notificationId) {
        notificationRepository.makeNotificationAsRead(notificationId);
    }
}
