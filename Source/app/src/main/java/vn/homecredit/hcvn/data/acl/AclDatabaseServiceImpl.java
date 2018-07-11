package vn.homecredit.hcvn.data.acl;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.di.PreferenceInfo;

@Singleton
public class AclDatabaseServiceImpl implements AclDatabaseService {

    private static final String PREF_KEY_ACL_ACCESS_TOKEN = "PREF_KEY_ACL_ACCESS_TOKEN";
    private final SharedPreferences mPrefs;

    @Inject
    public AclDatabaseServiceImpl(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAclAccessToken() {
        return mPrefs.getString(PREF_KEY_ACL_ACCESS_TOKEN, null);
    }

    @Override
    public void setAclAccessToken(String aclAccessToken) {
        mPrefs.edit().putString(PREF_KEY_ACL_ACCESS_TOKEN, aclAccessToken).apply();
    }
}
