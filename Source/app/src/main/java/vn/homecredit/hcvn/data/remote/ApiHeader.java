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

    private PublicApiHeader mPublicApiHeader;

    private ACLApiHeader mACLApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public ACLApiHeader getACLApiHeader() {
        return mACLApiHeader;
    }

    public static final class ProtectedApiHeader {
        @Expose
        @SerializedName("AccessToken")
        private String mAccessToken;

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization;

        public ProtectedApiHeader(String accessToken) {
            mAccessToken = accessToken;
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

    public static final class ACLApiHeader {

        @Expose
        @SerializedName("AccessToken")
        private String mAccessToken;

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization;

        @Inject
        public ACLApiHeader(String accessToken) {
            mAccessToken = accessToken;
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

}
