/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 4:40 PM, by Admin
 */

package vn.homecredit.hcvn.service;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import credoapp.CredoAppConfigurationManager;
import credoapp.CredoAppException;
import credoapp.CredoAppService;
import credoapp.PhoneNumber;
import credoapp.PhoneNumberConfiguration;
import vn.homecredit.hcvn.data.model.api.CredoConsent;
import vn.homecredit.hcvn.utils.DateUtils;

@Singleton
public class CredoServiceImpl implements CredoService {
    //private Context mContext;
    @Inject
    public CredoServiceImpl() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public ByteArrayOutputStream collectData(Context context) {
        try {
            CredoAppConfigurationManager configurationManager = new CredoAppConfigurationManager();
            configurationManager.IgnoreDeniedPermission = true;

            PhoneNumberConfiguration phoneNumberConfiguration = createPhoneNumberConfiguration();
            configurationManager.setPhoneNumberConfiguration(phoneNumberConfiguration);

            CredoAppService credoAppService = new CredoAppService(context);
            credoAppService.setConfiguration(configurationManager);
            return buildZipStream(credoAppService.collectData());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PhoneNumberConfiguration createPhoneNumberConfiguration() throws CredoAppException {
        PhoneNumber phoneNumber = new PhoneNumber("unique_id", "123456789");
        PhoneNumberConfiguration phoneNumberConfiguration = new PhoneNumberConfiguration();
        phoneNumberConfiguration.addItem(phoneNumber);
        return phoneNumberConfiguration;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ByteArrayOutputStream buildZipStream(String credoData) {
        byte[] dataToCompress = credoData.getBytes(StandardCharsets.UTF_8);

        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(dataToCompress.length);
            try {
                GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
                try {
                    zipStream.write(dataToCompress);
                } finally {
                    zipStream.close();
                }
            } finally {
                byteStream.close();
            }

            return byteStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getCredoConsent(String userId, String username, String appVersion) {
        CredoConsent credoConsent = new CredoConsent(
                userId,
                username,
                "Yes",
                DateUtils.getCurrentUTCTime(),
                Build.MODEL,
                Build.BRAND,
                Build.ID,
                Build.DEVICE,
                appVersion
        );

        Gson gson = new Gson();
        return gson.toJson(credoConsent);
    }
}
