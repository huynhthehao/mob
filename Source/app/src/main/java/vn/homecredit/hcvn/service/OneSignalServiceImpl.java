/*
 * OneSignalServiceImp.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 1:32 PM
 */

package vn.homecredit.hcvn.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.onesignal.shortcutbadger.ShortcutBadger;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.tracking.CommonEventAction;
import vn.homecredit.hcvn.service.tracking.CommonEventObjectType;
import vn.homecredit.hcvn.service.tracking.InternalTrackingServiceImpl;
import vn.homecredit.hcvn.service.tracking.TrackingService;
import vn.homecredit.hcvn.ui.notification.NotificationType;
import vn.homecredit.hcvn.ui.notification.NotificationsFragment;
import vn.homecredit.hcvn.ui.notification.manager.NotificationManager;
import vn.homecredit.hcvn.ui.notification.model.ClwResult;
import vn.homecredit.hcvn.ui.notification.model.ClwResultConverter;
import vn.homecredit.hcvn.ui.notification.model.NotificationAnalyticData;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.OfferConverter;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;

@Singleton
public class OneSignalServiceImpl implements OneSignalService {
    private final DeviceInfo deviceInfo;
    private PreferencesHelper preferencesHelper;
    private TrackingService trackingService;
    private InternalTrackingServiceImpl internalTrackingService;
    private NotificationManager notificationManager;

    @Inject
    public OneSignalServiceImpl(DeviceInfo deviceInfo, PreferencesHelper preferencesHelper,
                                TrackingService trackingService,
                                InternalTrackingServiceImpl internalTrackingService,
                                NotificationManager notificationManager) {
        this.deviceInfo = deviceInfo;
        this.preferencesHelper = preferencesHelper;
        this.trackingService = trackingService;
        this.internalTrackingService = internalTrackingService;
        this.notificationManager = notificationManager;
    }

    @Override
    public void sendTags(String key, String value) {
        OneSignal.sendTag(key, value);
    }

    @Override
    public void setSubscription(boolean subscribe) {
        OneSignal.setSubscription(subscribe);
    }

    @Override
    public void deleteTag(String key) {
        OneSignal.deleteTag(key);
    }

    @Override
    public String jsonString(Object object) {
        return object.toString();
    }

    @Override
    public void trygetPrivateId() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        String token = status.getSubscriptionStatus().getPushToken();
        deviceInfo.setPushToken(token);
    }

    @Override
    public void notificationReceived(Context context, OSNotification notification) {
        // Increase badge number
        int count = preferencesHelper.getCurrentBadgeCount();
        count = count + 1;
        ShortcutBadger.applyCount(context, count);
        preferencesHelper.setCurrentBadgeCount(count);

        // send broadcast receiver
        Intent intent = new Intent();
        intent.setAction(NotificationsFragment.ACTION_REFRESH_NOTIFICATIONS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        // get data, will open later
        //        JSONObject additionalData = notification.payload.additionalData;
        //        getNotificationModel(notification, additionalData);

        // tracking event
        trackingNotification(notification, true);
        // end tracking
    }

    @Override
    public void notificationOpenHandler(Context context, OSNotificationOpenResult result) {
        // tracking event
        trackingNotification(result.notification, false);
        // end tracking

        JSONObject additionalData = result.notification.payload.additionalData;
        NotificationModel notificationModel = getNotificationModel(result.notification, additionalData);
        notificationManager.openNotification(context, notificationModel);
    }

    private void trackingNotification(OSNotification notification, boolean isReceived) {
        NotificationAnalyticData notificationAnalyticData = getNotificationAnalyticData(notification);
        String eventAction = CommonEventAction.NOTIFICATION_RECEIVED.getValue();
        if (!isReceived)
            eventAction = CommonEventAction.NOTIFICATION_OPENED.getValue();
        trackingService.sendEvent(CommonEventObjectType.NOTIFICATION.getValue(), eventAction, notificationAnalyticData.getUtmContent());

        if (isReceived) {
            internalTrackingService.notificationReceived(notificationAnalyticData);
        }else {
            internalTrackingService.notificationOpened(notificationAnalyticData);
        }
    }

    @Nullable
    private NotificationAnalyticData getNotificationAnalyticData(OSNotification notification) {
        NotificationAnalyticData notificationAnalyticData = new NotificationAnalyticData();
        try {
            JSONObject addtionalData = notification.payload.additionalData;
            if (addtionalData == null || !addtionalData.has("analytics"))
                return notificationAnalyticData;
            JSONObject analyticsObject = addtionalData.optJSONObject("analytics");
            if (analyticsObject == null)
                return notificationAnalyticData;

            notificationAnalyticData = new Gson().fromJson(analyticsObject.toString(), NotificationAnalyticData.class);
        } catch (Exception ex) {
            notificationAnalyticData = new NotificationAnalyticData();
        }
        return notificationAnalyticData;
    }

    private NotificationModel getNotificationModel(OSNotification notification, JSONObject additionalData) {
        NotificationModel model = new NotificationModel();
        if (additionalData == null || !additionalData.has("Type")) {
            return model;
        }
        try {
            int type = additionalData.getInt("Type");
            NotificationType notificationType = NotificationType.getNotificationByType(type);
            model.setType(type);
            model.setMessageText(notification.payload.body);
            switch (notificationType) {
                case INCOMING:
                case REMINDER:
                    model.setContractNumber(getStringData(additionalData, "ContractNumber"));
                    model.setContractSKP(getStringData(additionalData, "ContractSKP"));
                    break;
                case MARKETING:
                    model.setMarketingUrlVi(getStringData(additionalData, "MarketingUrlVi"));
                    model.setMarketingUrlEn(getStringData(additionalData, "MarketingUrlEn"));
                    break;
                case CRM:
                    if (additionalData.has("Offer")) {
                        OfferModel offerModel = new OfferConverter().fromStringToObject(additionalData.getJSONObject("Offer").toString());
                        if (offerModel != null)
                            model.setOffer(offerModel);
                    }
                    break;
                case CLW_APPLICATION_LOAN_EXPIRED:
                case CLW_APPLICATION_LOAN_RESULT:
                case CLW_APPLICATION_LOAN_REMIND_SIGNING:
                    if (additionalData.has("ClwResult")) {
                        ClwResult clwResult = new ClwResultConverter().fromStringToObject(additionalData.getJSONObject("ClwResult").toString());
                        if (clwResult != null)
                            model.setClwResult(clwResult);
                    }
                    break;
            }
            if (additionalData.has("Id")) {
                model.setId(getStringData(additionalData, "Id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

    private String getStringData(JSONObject object, String key) {
        if (object.has(key)) {
            try {
                return object.getString(key);
            } catch (JSONException e) {
            }
        }
        return "";
    }
}
