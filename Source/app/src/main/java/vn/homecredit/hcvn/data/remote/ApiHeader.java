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
    private final AclApiHeader mAclApiHeader;

    private PublicApiHeader mPublicApiHeader;
    private PayooApiHeader mPayooApiHeader;
    private PosApiHeader mPosApiHeader;
    private ClwMapApiHeader mClwMapApiHeader;
    private DisbursementApiHeader mDisbursementApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader, AclApiHeader aclApiHeader, PayooApiHeader payooApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
        mAclApiHeader = aclApiHeader;
        mPayooApiHeader = payooApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public AclApiHeader getAclApiHeader() {
        return mAclApiHeader;
    }

    public PayooApiHeader getPayooApiHeader() {
        return mPayooApiHeader;
    }

    public PosApiHeader getPosApiHeader() {
        return mPosApiHeader;
    }

    public DisbursementApiHeader getDisbursementApiHeader() {
        return mDisbursementApiHeader;
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

    public static final class PublicApiHeader {
        @Inject
        public PublicApiHeader() {
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

    public static final class PosApiHeader {
        @Expose
        @SerializedName("Content-Type")
        private String mContentType;

        @Inject
        public PosApiHeader() {
            mContentType = "application/json";
        }
    }

    public static final class ClwMapApiHeader {
        @Expose
        @SerializedName("Content-Type")
        private String mContentType;

        @Inject
        public ClwMapApiHeader() {
            mContentType = "application/json";
        }
    }

    public static final class DisbursementApiHeader {
        @Expose
        @SerializedName("Content-Type")
        private String mContentType;

        @Inject
        public DisbursementApiHeader() {
            mContentType = "application/json";
        }
    }

    public static final class PaymentApiHeader {
        @Expose
        @SerializedName("Content-Type")
        private String mContentType;

        @Inject
        public PaymentApiHeader() {
            mContentType = "application/json";
        }
    }

}
