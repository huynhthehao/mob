/*
 * ApiHeader.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 2:02 PM
 */

package vn.homecredit.hcvn.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApiHeader {
    private ProtectedApiHeader mProtectedApiHeader;
    private AclApiHeader aclApiHeader;
    private PayooApiHeader payooApiHeader;

    @Inject
    public ApiHeader(ProtectedApiHeader protectedApiHeader, AclApiHeader aclApiHeader, PayooApiHeader payooApiHeader) {
        mProtectedApiHeader = protectedApiHeader;
        this.aclApiHeader = aclApiHeader;
        this.payooApiHeader = payooApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public AclApiHeader getAclApiHeader() {
        return aclApiHeader;
    }

    public static final class ProtectedApiHeader {

        @SerializedName("AccessToken")
        private String mAccessToken;

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization;

        public ProtectedApiHeader(String accessToken) {
            setAccessToken(accessToken);
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
            mAuthorization = String.format("Bearer %s", mAccessToken);
        }

        public String getAuthorization() {
            return mAuthorization;
        }
    }

    public static final class AclApiHeader {

        @Expose
        @SerializedName("AccessToken")
        private String mAccessToken;

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization;

        @Inject
        public AclApiHeader(String accessToken) {
            setAccessToken(accessToken);
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
            mAuthorization = String.format("Bearer %s", mAccessToken);
        }

        public String getAuthorization() {
            return mAuthorization;
        }
    }

    public PayooApiHeader getPayooApiHeader() {
        return payooApiHeader;
    }

    public static final class PayooApiHeader {
        @Expose
        @SerializedName("token")
        private String mToken;

        @Expose
        @SerializedName("user")
        private String mUser;

        @Inject
        public PayooApiHeader(String token, String user) {
            mToken = token;
            mUser = user;
        }
    }



}
