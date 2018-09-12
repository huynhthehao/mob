package vn.homecredit.hcvn.ui.notification.manager;

import android.content.Context;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.notification.NotificationType;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.offers.OfferActivity;
import vn.homecredit.hcvn.utils.AppUtils;
import vn.homecredit.hcvn.utils.CountryValue;

public class NotificationManager {

    private Context context;
    private PreferencesHelper preferencesHelper;
    private NotificationRepository notificationRepository;

    @Inject
    public NotificationManager(Context context, PreferencesHelper preferencesHelper, NotificationRepository notificationRepository) {
        this.context = context;
        this.preferencesHelper = preferencesHelper;
        this.notificationRepository = notificationRepository;
    }

    public void openNotification(NotificationModel model) {
        markAsReadNotificationToServer(model);
        NotificationType notificationType = NotificationType.getNotificationByType(model.getType());
        switch (notificationType) {
            case INCOMING:
            case REMINDER:
                //TODO
                break;
            case MARKETING:
                AppUtils.openExternalBrowser(context, getMarketingUrl(model));
                break;
            case CRM:
                OfferActivity.start(context, model.getOffer());
                break;
            case CLW_APPLICATION_LOAN_EXPIRED:
            case CLW_APPLICATION_LOAN_RESULT:
            case CLW_APPLICATION_LOAN_REMIND_SIGNING:
                //TODO
//                    if (additionalData.has("ClwResult")) {
//                        ClwResult clwResult = new ClwResultConverter().fromStringToObject(additionalData.getJSONObject("ClwResult").toString());
//                        if (clwResult != null)
//                            model.setClwResult(clwResult);
//                    }
                break;
        }
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
