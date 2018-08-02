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
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;
import vn.homecredit.hcvn.data.model.DeviceInfoModel;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.ui.notification.NotificationType;
import vn.homecredit.hcvn.ui.notification.NotificationsFragment;
import vn.homecredit.hcvn.ui.notification.model.ClwResult;
import vn.homecredit.hcvn.ui.notification.model.ClwResultConverter;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.OfferConverter;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;

import static vn.homecredit.hcvn.ui.notification.NotificationType.INCOMING;

@Singleton
public class OneSignalServiceImpl implements OneSignalService {
    private final DeviceInfo mDeviceInfo;
    private PreferencesHelper preferencesHelper;

    @Inject
    public OneSignalServiceImpl(DeviceInfo deviceInfo, PreferencesHelper preferencesHelper) {
        mDeviceInfo = deviceInfo;
        this.preferencesHelper = preferencesHelper;
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
    public void tryGetPlayerId() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        String userID = status.getSubscriptionStatus().getUserId();
        String token = status.getSubscriptionStatus().getPushToken();
        mDeviceInfo.setPlayerId(userID);
        mDeviceInfo.setPushToken(token);
    }

    @Override
    public void notificationReceived(Context context, OSNotification notification) {
        // send broadcast receiver
        Intent intent = new Intent();
        intent.setAction(NotificationsFragment.ACTION_REFRESH_NOTIFICATIONS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        // get data
        JSONObject additionalData = notification.payload.additionalData;
        getNotificationModel(notification, additionalData);
    }

    @Override
    public void notificationOpenHandler(Context context, OSNotificationOpenResult result) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(HomeActivity.BUNDLE_SELECT_NOTIFICATION_TAB, true);
        if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            intent = new Intent(context, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getNotificationModel(OSNotification notification, JSONObject additionalData) {
        NotificationModel model = new NotificationModel();
        if (additionalData == null || !additionalData.has("Type")) {
            return;
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
